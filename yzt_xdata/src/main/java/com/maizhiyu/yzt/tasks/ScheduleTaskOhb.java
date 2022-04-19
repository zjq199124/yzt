package com.maizhiyu.yzt.tasks;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.fastjson.JSONObject;
import com.maizhiyu.yzt.entity.BuOutpatient;
import com.maizhiyu.yzt.entity.BuPatient;
import com.maizhiyu.yzt.feign.YztApiClient;
import com.maizhiyu.yzt.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Date;
import java.util.*;


@Slf4j
@Component
@EnableScheduling
@ConditionalOnProperty(prefix = "xdata.ohb", name = "enable", havingValue = "true")
public class ScheduleTaskOhb {

    private DataSource dataSource;

    @Autowired
    private YztApiClient yztClient;

    @Value("${xdata.ohb.outpatient-enable}")
    private boolean outpatientEnable = true;

    @Value("${xdata.ohb.diagnose-enable}")
    private boolean diagnoseEnable = true;

    @Value("${xdata.ohb.prescription-enable}")
    private boolean prescriptionEnable = true;

    @Value("${xdata.ohb.customerId}")
    private Long customerId;

    @Value("${xdata.ohb.sql-ghd}")
    private String sqlGhd;

    @Value("${xdata.ohb.diagnose-minute-start}")
    private Integer diagnoseMinuteStart = -10;

    @Value("${xdata.ohb.diagnose-minute-end}")
    private Integer diagnoseMinuteEnd = -1;

    @Value("${xdata.ohb.prescription-minute-start}")
    private Integer prescriptionMinuteStart = -10;

    @Value("${xdata.ohb.prescription-minute-end}")
    private Integer prescriptionMinuteEnd = -1;

    private HashMap<Long, Long> mapDepartmentHisToYpt = new HashMap<>();

    private HashMap<Long, Long> mapDepartmentYptToHis = new HashMap<>();

    private HashMap<Long, Long> mapDoctor = new HashMap<>();


    @PostConstruct
    public void init() {
        try {
            log.info("初始化连接池");
            //加载配置文件
            Properties properties = new Properties();
            properties.load(this.getClass().getResourceAsStream("/druid.properties"));
            //获取数据库连接池对象
            dataSource = DruidDataSourceFactory.createDataSource(properties);

            log.info("初始化科室映射");
            InputStream inputStreamDepartment = this.getClass().getResourceAsStream("/data/ohb/department.txt");
            BufferedReader bufferedReaderDepartment = new BufferedReader(new InputStreamReader(inputStreamDepartment));
            String lineDepartment = null;
            while ((lineDepartment = bufferedReaderDepartment.readLine()) != null) {
                lineDepartment = lineDepartment.trim();
                if (lineDepartment.length() < 3) continue;
                if (lineDepartment.startsWith("#")) continue;
                String[] arr = lineDepartment.split("-");
                if (arr.length == 2) {
                    try {
                        if (arr[0].trim().length() > 0 && arr[1].trim().length() > 0) {
                            Long departmentYpt = Long.valueOf(arr[0].trim());
                            Long departmentHis = Long.valueOf(arr[1].trim());
                            mapDepartmentYptToHis.put(departmentYpt, departmentHis);
                            mapDepartmentHisToYpt.put(departmentHis, departmentYpt);
                        }
                    } catch (Exception e) {
                        log.warn("科室映射解析异常 " + lineDepartment);
                        e.printStackTrace();
                    }
                }
            }

            log.info("初始化医生映射");
            InputStream inputStreamDoctor = this.getClass().getResourceAsStream("/data/ohb/doctor.txt");
            BufferedReader bufferedReaderDoctor = new BufferedReader(new InputStreamReader(inputStreamDoctor));
            String lineDoctor = null;
            while ((lineDoctor = bufferedReaderDoctor.readLine()) != null) {
                lineDoctor = lineDoctor.trim();
                if (lineDoctor.length() < 3) continue;
                if (lineDoctor.startsWith("#")) continue;
                String[] arr = lineDoctor.split("-");
                if (arr.length == 2) {
                    try {
                        if (arr[0].trim().length() > 0 && arr[1].trim().length() > 0) {
                            Long doctorHis = Long.valueOf(arr[0].trim());
                            Long doctorYpt = Long.valueOf(arr[1].trim());
                            mapDoctor.put(doctorHis, doctorYpt);
                        }
                    } catch (Exception e) {
                        log.warn("医生映射解析异常 " + lineDoctor);
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            log.warn("数据库初始化异常", e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }


    @Scheduled(cron="${xdata.ohb.cron}")
    public void scheduleTask() {
        try {
            log.info("");
            log.info("================================================================================");

            log.info("获取数据库连接");
            Connection connection = dataSource.getConnection();

            try {
                // 处理挂号信息（HIS -> YPT）
                if (outpatientEnable) {
                    processOutpatient(connection);
                }

                // 处理诊断信息（YPT -> HIS）
                if (diagnoseEnable) {
                    processDiagnose(connection);
                }

                // 处理处方信息（YPT -> HIS）
                if (prescriptionEnable) {
                    processPrescription(connection);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 归还连接
                log.info("归还数据库连接");
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            log.info("================================================================================");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void processOutpatient(Connection connection) throws SQLException {
        // 查询数据库时间
        String sqlTime = "SELECT to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') FROM dual";
        Statement stmtTime = connection.createStatement();
        ResultSet rsTime = stmtTime.executeQuery(sqlTime);
        rsTime.next();
        String time = rsTime.getString(1);

        // 查询挂号信息（HIS）
        log.info("================================================================================");
        log.info("查询挂号数据 [{}] {}", time, sqlGhd);
        Statement stmtGhd = connection.createStatement();
        ResultSet rsGhd = stmtGhd.executeQuery(sqlGhd);

        // 处理每个挂号
        while (rsGhd.next()) {
            try {
                Long outpatientId = rsGhd.getLong("GHD_ID");
                log.info("--------------------------------------------------------------------------------");
                log.info("处理挂号 " + outpatientId);

                // 查询HIS系统内患者信息
                Integer patientId = rsGhd.getInt("BR_ID");
                String sqlBrjbxxb = "" +
                        "SELECT * " +
                        "FROM MZ_BRJBXXB " +
                        "WHERE BR_ID = ?";
                PreparedStatement ptmtBrjbxxb = connection.prepareStatement(sqlBrjbxxb);
                ptmtBrjbxxb.setInt(1, patientId);
                ResultSet rsBrjbxxb = ptmtBrjbxxb.executeQuery();
                rsBrjbxxb.next();

                // 生成患者信息
                BuPatient patient = new BuPatient();
                patient.setCustomerId(customerId);
                patient.setName(rsBrjbxxb.getString("XM"));
                patient.setNl(rsBrjbxxb.getInt("NL"));
                patient.setSex(rsBrjbxxb.getInt("XB"));
                patient.setIdcard(rsBrjbxxb.getString("SFZH"));
                patient.setPhone(rsBrjbxxb.getString("LXDH"));

                JSONObject jobjPatient = new JSONObject();
                jobjPatient.put("brId", patientId);
                String patientExtra = JSONObject.toJSONString(jobjPatient);
                patient.setExtra(patientExtra);

                // 更新患者信息
                log.info("更新患者信息 " + patient);
                Result<BuPatient> resultPatient = yztClient.addPatientOrUpdate(patient);
                if (resultPatient.getCode() == 0) {
                    log.info("更新患者成功 " + resultPatient);
                } else {
                    log.warn("更新患者失败 " + resultPatient);
                }

                // 生成挂号信息
                BuOutpatient outpatient = new BuOutpatient();
                outpatient.setCustomerId((long)customerId);
                outpatient.setDepartmentId(mapDepartmentHisToYpt.get(rsGhd.getLong("GHKS_ID")));
                outpatient.setPatientId(resultPatient.getData().getId());
                outpatient.setTime(new java.util.Date(rsGhd.getTimestamp("GHRQ").getTime()));

                JSONObject jobjOutpatient = new JSONObject();
                jobjOutpatient.put("ghdId", outpatientId);
                String outpatientExtra = JSONObject.toJSONString(jobjOutpatient);
                outpatient.setExtra(outpatientExtra);

                // 更新挂号信息
                log.info("更新挂号信息 " + outpatient);
                Result<BuOutpatient> resultOutpatient = yztClient.addOutpatient(outpatient);
                if (resultOutpatient.getCode() == 0) {
                    log.info("更新挂号成功 " + resultOutpatient);
                } else {
                    log.warn("更新挂号失败 " + resultOutpatient);
                }
            } catch (Exception e) {
                log.error("处理挂号异常");
                e.printStackTrace();
            }
        }
    }

    private void processDiagnose(Connection connection) {
        // 查询诊断信息（云平台）
        Date date = new Date();
        Date startDate = DateUtils.addMinutes(date, diagnoseMinuteStart);
        Date endDate = DateUtils.addMinutes(date, diagnoseMinuteEnd);
        String start = DateFormatUtils.format(startDate, "yyyy-MM-dd HH:mm:00");
        String end = DateFormatUtils.format(endDate, "yyyy-MM-dd HH:mm:00");

        // 查询诊断数据
        log.info("================================================================================");
        log.info("查询诊断数据 {}, {}, {}", customerId, start, end);
        Result<List<Map<String, Object>>> result = yztClient.getDiagnoseList(customerId, start, end);

        // 处理每个诊断
        for (Map<String, Object> map : result.getData()) {
            try {
                log.info("--------------------------------------------------------------------------------");
                log.info("处理诊断 " + map);

                // 挂号信息
                Map<String, Object> outpatient = (Map<String, Object>) map.get("outpatient");
                String outpatientExtra = (String) outpatient.get("extra");
                Long ghdId = (long) JSONObject.parseObject(outpatientExtra).get("ghdId");
                if (ghdId == null) {
                    log.warn("挂号ID异常 " + outpatientExtra);
                    continue;
                }

                // 病人信息
                Map<String, Object> patient = (Map<String, Object>) map.get("patient");
                String patientExtra = (String) patient.get("extra");
                Integer brId = (int) JSONObject.parseObject(patientExtra).get("brId");
                if (brId == null) {
                    log.warn("病人ID异常 " + patientExtra);
                    continue;
                }

                // 科室映射
                Long departmentYpt = Long.valueOf((int) map.get("departmentId"));
                Long departmentHis = mapDepartmentYptToHis.get(departmentYpt);
                if (departmentHis == null) {
                    log.warn("科室ID异常 " + departmentYpt);
                    // continue;
                }

                // 医生映射
                Long doctorYpt = Long.valueOf((int) map.get("doctorId"));
                Long doctorHis = mapDoctor.get(doctorYpt);
                if (doctorHis == null) {
                    log.warn("医生ID异常 " + doctorYpt);
                    continue;
                }

                // 处理病历表
                Integer yptId = (Integer) map.get("id");
                Long mzblId = System.currentTimeMillis() / 100 - 16000000000L;
                String cfz = "";
                String zs = (String) map.get("disease");
                String xbs = (String) map.get("symptoms");
                String gqs = (String) map.get("medicalHistory");
                String gms = (String) map.get("allergicHistory");
                String jzs = (String) map.get("familyHistory");
                Long kzks = departmentHis;
                Long kzys = doctorHis;
                java.sql.Date kzrq = new java.sql.Date(new Date().getTime());

                // 重复判断
                String sqlDiagnoseExist = "" +
                        "SELECT * " +
                        "FROM MZ_BLB " +
                        "WHERE YPT_ID = ?";
                PreparedStatement ptmtDiagnoseExist = connection.prepareStatement(sqlDiagnoseExist);
                ptmtDiagnoseExist.setString(1, "" + yptId);
                ResultSet resDiagnoseExist = ptmtDiagnoseExist.executeQuery();

                // 新增病历
                if (resDiagnoseExist.next() == false) {
                    log.info("新增病历 {}, {}, {}, {}", yptId, ghdId, brId, kzrq);
                    String sqlBlb = "INSERT INTO " +
                            "MZ_BLB (YPT_ID, MZBL_ID, GHD_ID, BR_ID, CFZ, ZS, XBS, GQS, GMS, JZS, KZKS, KZYS, KZRQ) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement ptmtBlb = connection.prepareStatement(sqlBlb);
                    ptmtBlb.setString(1, "" + yptId);
                    ptmtBlb.setLong(2, mzblId);
                    ptmtBlb.setString(3, "" + ghdId);
                    ptmtBlb.setInt(4, brId);
                    ptmtBlb.setString(5, cfz);
                    ptmtBlb.setString(6, zs);
                    ptmtBlb.setString(7, xbs);
                    ptmtBlb.setString(8, gqs);
                    ptmtBlb.setString(9, gms);
                    ptmtBlb.setString(10, jzs);
                    ptmtBlb.setLong(11, kzks);
                    ptmtBlb.setLong(12, kzys);
                    ptmtBlb.setDate(13, kzrq);
                    int res1 = ptmtBlb.executeUpdate();
                }

                // 更新病历
                else {
                    log.info("更新病历 {}, {}, {}, {}", yptId, ghdId, brId, kzrq);
                    String sqlBlb = "UPDATE MZ_BLB " +
                            "SET ZS=?, XBS=?, GQS=?, GMS=?, JZS=?, KZKS=?, KZYS=?, KZRQ=? " +
                            "WHERE YPT_ID=?";
                    PreparedStatement ptmtBlb = connection.prepareStatement(sqlBlb);
                    ptmtBlb.setString(1, zs);
                    ptmtBlb.setString(2, xbs);
                    ptmtBlb.setString(3, gqs);
                    ptmtBlb.setString(4, gms);
                    ptmtBlb.setString(5, jzs);
                    ptmtBlb.setLong(6, kzks);
                    ptmtBlb.setLong(7, kzys);
                    ptmtBlb.setDate(8, kzrq);
                    ptmtBlb.setString(9, "" + yptId);
                    int res2 = ptmtBlb.executeUpdate();
                }
            } catch (Exception e) {
                log.error("处理诊断异常");
                e.printStackTrace();
            }
        }
    }

    private void processPrescription(Connection connection) throws SQLException {
        // 查询处方信息（云平台）
        Date date = new Date();
        Date startDate = DateUtils.addMinutes(date, prescriptionMinuteStart);
        Date endDate = DateUtils.addMinutes(date, prescriptionMinuteEnd);
        String start = DateFormatUtils.format(startDate, "yyyy-MM-dd HH:mm:00");
        String end = DateFormatUtils.format(endDate, "yyyy-MM-dd HH:mm:00");

        // 查询处方数据
        log.info("================================================================================");
        log.info("查询处方数据 {}, {}, {}", customerId, start, end);
        Result<List<Map<String, Object>>> result = yztClient.getPrescriptionList(customerId, start, end);

        // 处理每个处方
        for (Map<String, Object> map : result.getData()) {
            try {
                log.info("--------------------------------------------------------------------------------");
                log.info("处理处方 " + map);

                // 病人信息
                Map<String, Object> patient = (Map<String, Object>) map.get("patient");
                String patientExtra = (String) patient.get("extra");
                Integer brId = (int) JSONObject.parseObject(patientExtra).get("brId");
                if (brId == null) {
                    log.warn("病人ID异常 " + patientExtra);
                    continue;
                }

                // 挂号信息
                Map<String, Object> outpatient = (Map<String, Object>) map.get("outpatient");
                String outpatientExtra = (String) outpatient.get("extra");
                Long ghdId = (long) JSONObject.parseObject(outpatientExtra).get("ghdId");
                if (ghdId == null) {
                    log.warn("挂号ID异常 " + outpatientExtra);
                    continue;
                }

                // 科室映射
                Long departmentYpt = Long.valueOf((int) outpatient.get("departmentId"));
                Long departmentHis = mapDepartmentYptToHis.get(departmentYpt);
                if (departmentHis == null) {
                    log.warn("科室ID异常 " + departmentYpt);
                    // continue;
                }

                // 医生映射
                Long doctorYpt = Long.valueOf((int) map.get("doctorId"));
                Long doctorHis = mapDoctor.get(doctorYpt);
                if (doctorHis == null) {
                    log.warn("医生ID异常 " + doctorYpt);
                    continue;
                }

                // 查询病历ID
                Long diagnoseId = Long.valueOf((int) map.get("diagnoseId"));
                String sqlBlb = "" +
                        "SELECT * " +
                        "FROM MZ_BLB " +
                        "WHERE YPT_ID = ?";
                PreparedStatement ptmtBlb = connection.prepareStatement(sqlBlb);
                ptmtBlb.setString(1, "" + diagnoseId);
                ResultSet resBlb = ptmtBlb.executeQuery();
                if (!resBlb.next()) {
                    log.info("病历记录不存在 " + diagnoseId);
                    continue;
                }
                Long mzblId = resBlb.getLong("MZBL_ID");

                // 准备字段
                String yptId = (String) map.get("code");
                Long now = System.currentTimeMillis();
                String ywdId = "" + now;
                Integer ts = (int) map.get("dayCount");
                java.sql.Date chrq = new java.sql.Date(now);

                // 重复判断
                String sqlExist = "" +
                        "SELECT * " +
                        "FROM MZ_YWD " +
                        "WHERE YPT_ID = ?";
                PreparedStatement ptmtExist = connection.prepareStatement(sqlExist);
                ptmtExist.setString(1, yptId);
                ResultSet resExist = ptmtExist.executeQuery();
                if (resExist.next()) {
                    log.info("处方已存在 " + yptId);
                    continue;
                }

                // 处理处方
                log.info("新增处方 {}, {}, {}, {}, {}, {}, {}", yptId, ghdId, mzblId, ywdId, brId, ts, chrq);
                String sqlYwd = "INSERT INTO " +
                        "MZ_YWD (YPT_ID, GHD_ID, MZBL_ID, YWD_ID, BR_ID, CFTS, CFRQ, KDKS, KDYS, CFLX, ZT, DYSKSBH, ZXTBH_ID) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 2, 1, 4, 6)";
                PreparedStatement ptmtYwd = connection.prepareStatement(sqlYwd);
                ptmtYwd.setString(1, yptId);
                ptmtYwd.setString(2, "" + ghdId);
                ptmtYwd.setLong(3, mzblId);
                ptmtYwd.setString(4, ywdId);
                ptmtYwd.setInt(5, brId);
                ptmtYwd.setInt(6, ts);
                ptmtYwd.setDate(7, chrq);
                ptmtYwd.setLong(8, departmentHis);
                ptmtYwd.setLong(9, doctorHis);
                int res1 = ptmtYwd.executeUpdate();
                // log.info("新增处方结果 " + res1);

                // 处理药材
                Integer xh = 1;
                Double je = 0.0;
                ArrayList<Map<String, Object>> itemList = (ArrayList<Map<String, Object>>) map.get("itemList");
                for (Map<String, Object> item : itemList) {
                    log.info("药材 " + item);
                    // 解析字段
                    String name = (String) item.get("name");
                    Integer yl = ((Double) item.get("dosage")).intValue();
                    Integer ypggcdId = 0;
                    Integer yldw = 0;
                    Integer dw = 0;
                    Integer dsl = ((Double) item.get("dosage")).intValue();
                    Integer sl = ((Double) item.get("dosage")).intValue();
                    Double lsjg = 0.0;
                    Double dje = 0.0;
                    // 查询药材价格
                    String sqlYp = "" +
                            "SELECT * " +
                            "FROM DM_GGCD A JOIN DM_YPZD B ON A.YPZD_ID = B.YPZD_ID " +
                            "WHERE B.YPTYM = ?";
                    PreparedStatement ptmtYp = connection.prepareStatement(sqlYp);
                    ptmtYp.setString(1, name);
                    ResultSet rsYp = ptmtYp.executeQuery();
                    if (rsYp.next()) {
                        ypggcdId = rsYp.getInt("YPGGCD_ID");
                        lsjg = rsYp.getDouble("JJDWLSJ");
                        dje = lsjg * yl;
                        je += dje;
                        log.info("价格 {}, {}, {}", ypggcdId, lsjg, dje);
                    } else {
                        log.warn("药材不存在 {}", name);
                        continue;
                    }
                    // 添加费用明细记录
                    String sqlFymxb = "" +
                            "INSERT INTO " +
                            "MZ_FYMXB (YWD_ID, YPGGCD_ID, YL, YLDW, TS, DW, D_SL, SL, LSJG, D_JE, JE, XH, BZS, ZT)" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1, 1) ";
                    PreparedStatement ptmtFymxb = connection.prepareStatement(sqlFymxb);
                    ptmtFymxb.setString(1, ywdId);
                    ptmtFymxb.setInt(2, ypggcdId);
                    ptmtFymxb.setInt(3, yl);
                    ptmtFymxb.setInt(4, yldw);
                    ptmtFymxb.setInt(5, ts);
                    ptmtFymxb.setInt(6, dw);
                    ptmtFymxb.setInt(7, dsl);
                    ptmtFymxb.setInt(8, sl);
                    ptmtFymxb.setDouble(9, lsjg);
                    ptmtFymxb.setDouble(10, dje);
                    ptmtFymxb.setDouble(11, je);
                    ptmtFymxb.setInt(12, xh++);
                    int resFymxb = ptmtFymxb.executeUpdate();
                    // log.info("药材结果 " + resFymxb);
                }

                // 更新业务单金额
                log.info("更新处方 {}, {}", ywdId, je);
                String sqlYwd2 = "" +
                        "UPDATE MZ_YWD " +
                        "SET CFJE = ? " +
                        "WHERE YWD_ID = ?";
                PreparedStatement ptmtYwd2 = connection.prepareStatement(sqlYwd2);
                ptmtYwd2.setDouble(1, je);
                ptmtYwd2.setString(2, ywdId);
                int res2 = ptmtYwd2.executeUpdate();
                // log.info("更新业务单 " + res2);
            } catch (Exception e) {
                log.error("处理处方异常");
                e.printStackTrace();
            }
        }
    }
}
