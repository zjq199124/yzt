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

    private HashMap<String, String> mapDisease = new HashMap<>();

    private HashMap<String, String> mapZhiliao = new HashMap<>();

    private HashMap<String, Integer> mapUnit = new HashMap<>();


    @PostConstruct
    public void init() {
        try {
            log.info("【初始化连接池】");
            //加载配置文件
            Properties properties = new Properties();
            properties.load(this.getClass().getResourceAsStream("/druid.properties"));
            //获取数据库连接池对象
            dataSource = DruidDataSourceFactory.createDataSource(properties);

            log.info("【初始化科室映射】");
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
                            log.info(departmentYpt + "-" + departmentHis);
                        }
                    } catch (Exception e) {
                        log.warn("科室映射解析异常 " + lineDepartment);
                        e.printStackTrace();
                    }
                }
            }

            log.info("【初始化医生映射】");
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
                            log.info(doctorYpt + "-" + doctorHis);
                        }
                    } catch (Exception e) {
                        log.warn("医生映射解析异常 " + lineDoctor);
                        e.printStackTrace();
                    }
                }
            }

            log.info("【初始化疾病映射】");
            InputStream inputStreamDisease = this.getClass().getResourceAsStream("/data/ohb/disease.txt");
            BufferedReader bufferedReaderDisease = new BufferedReader(new InputStreamReader(inputStreamDisease));
            String lineDisease = null;
            while ((lineDisease = bufferedReaderDisease.readLine()) != null) {
                lineDisease = lineDisease.trim();
                if (lineDisease.length() < 2) continue;
                if (lineDisease.startsWith("#")) continue;
                String[] arr = lineDisease.split("-");
                if (arr.length == 3) {
                    try {
                        if (arr[0].trim().length() > 0 && arr[1].trim().length() > 0 && arr[2].trim().length() > 0) {
                            String diseaseYpt = arr[0].trim();
                            String string = arr[1].trim() + "-" + arr[2].trim();
                            mapDisease.put(diseaseYpt, string);
                            log.info(diseaseYpt + "-" + string);
                        }
                    } catch (Exception e) {
                        log.warn("疾病映射解析异常 " + lineDisease);
                        e.printStackTrace();
                    }
                }
            }

            log.info("【初始化治疗映射】");
            InputStream inputStreamZhiliao = this.getClass().getResourceAsStream("/data/ohb/zhiliao.txt");
            BufferedReader bufferedReaderZhiliao = new BufferedReader(new InputStreamReader(inputStreamZhiliao));
            String lineZhiliao = null;
            while ((lineZhiliao = bufferedReaderZhiliao.readLine()) != null) {
                lineZhiliao = lineZhiliao.trim();
                if (lineZhiliao.length() < 2) continue;
                if (lineZhiliao.startsWith("#")) continue;
                String[] arr = lineZhiliao.split("-");
                if (arr.length == 2) {
                    try {
                        if (arr[0].trim().length() > 0 && arr[1].trim().length() > 0) {
                            String zhiliaoYpt = arr[0].trim();
                            String zhiliaoHis = arr[1].trim();
                            mapZhiliao.put(zhiliaoYpt, zhiliaoHis);
                            log.info(zhiliaoYpt + " - " + zhiliaoHis);
                        }
                    } catch (Exception e) {
                        log.warn("治疗映射解析异常 " + lineZhiliao);
                        e.printStackTrace();
                    }
                }
            }

            log.info("【初始化单位映射】");
            InputStream inputStreamUnit = this.getClass().getResourceAsStream("/data/ohb/unit.txt");
            BufferedReader bufferedReaderUnit = new BufferedReader(new InputStreamReader(inputStreamUnit));
            String lineUnit = null;
            while ((lineUnit = bufferedReaderUnit.readLine()) != null) {
                lineUnit = lineUnit.trim();
                if (lineUnit.length() < 2) continue;
                if (lineUnit.startsWith("#")) continue;
                String[] arr = lineUnit.split("-");
                if (arr.length == 2) {
                    try {
                        if (arr[0].trim().length() > 0 && arr[1].trim().length() > 0) {
                            String unitYpt = arr[0].trim();
                            Integer unitHis = Integer.valueOf(arr[1].trim());
                            mapUnit.put(unitYpt, unitHis);
                            log.info(unitYpt + " - " + unitHis);
                        }
                    } catch (Exception e) {
                        log.warn("单位映射解析异常 " + lineUnit);
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
                Long mzblId = doProcessBlb(connection, ghdId, brId, departmentHis, doctorHis, map);

                // 诊断映射
                String diseaseYpt = (String) map.get("disease");
                String diseaseHis = mapDisease.get(diseaseYpt);
                if (diseaseHis == null) {
                    log.warn("疾病ID异常 " + diseaseYpt);
                    continue;
                }
                String[] arr = diseaseHis.split("-");
                String icdbm = arr[0].trim();
                String zdmc = arr[1].trim();

                // 处理诊断记录表
                doProcessZdjlb(connection, mzblId, icdbm, zdmc, map);

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

                // 查询病历
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

                // 查询诊断
                String icdbm = "";
                String zdmc = "";
                String sqlZdjlb = "" +
                        "SELECT * " +
                        "FROM MZ_ZDJLB " +
                        "WHERE MZBL_ID = ?";
                PreparedStatement ptmtZdjlb = connection.prepareStatement(sqlZdjlb);
                ptmtZdjlb.setLong(1, mzblId);
                ResultSet resZdjlb = ptmtZdjlb.executeQuery();
                if (!resZdjlb.next()) {
                    log.info("诊断记录不存在 " + diagnoseId);
                    continue;
                } else {
                    icdbm = resZdjlb.getString("ICD_BM");
                    zdmc = resZdjlb.getString("ZDMC");
                }

                // 处理处方
                doProcessYwd(connection, ghdId, mzblId, brId, departmentHis, doctorHis, icdbm, zdmc, map);

            } catch (Exception e) {
                log.error("处理处方异常");
                e.printStackTrace();
            }
        }
    }


    private Long doProcessBlb(Connection connection, Long ghdId, Integer brId, Long kzks, Long kzys, Map<String, Object> map) throws SQLException {

        Long mzblId;

        // 处理病历表
        Integer yptId = (Integer) map.get("id");
        String cfz = "";
        String zs = (String) map.get("disease");
        String xbs = (String) map.get("symptoms");
        String gqs = (String) map.get("medicalHistory");
        String gms = (String) map.get("allergicHistory");
        String jzs = (String) map.get("familyHistory");
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

            // 生成门诊病历ID
            mzblId = System.currentTimeMillis() / 100 - 16000000000L;

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

            // 获取门诊病历ID
            mzblId = resDiagnoseExist.getLong("MZBL_ID");

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

        return mzblId;
    }


    private void doProcessZdjlb(Connection connection, Long mzblId, String icdbm, String zdmc, Map<String, Object> map) throws SQLException {
        // 处理诊断记录表
        Integer yptId = (Integer) map.get("id");
        String disease = (String) map.get("disease");

        // 重复判断
        String sqlZdjlbExist = "" +
                "SELECT * " +
                "FROM MZ_ZDJLB " +
                "WHERE YPT_ID = ?";
        PreparedStatement ptmtZdjlbExist = connection.prepareStatement(sqlZdjlbExist);
        ptmtZdjlbExist.setString(1, "" + yptId);
        ResultSet resZdjlbExist = ptmtZdjlbExist.executeQuery();

        // 新增记录
        if (resZdjlbExist.next() == false) {
            log.info("新增诊断记录 {}, {}, {}, {}", yptId, disease, icdbm, zdmc);
            String sqlBlb = "INSERT INTO " +
                    "MZ_ZDJLB (YPT_ID, MZBL_ID, XH, ICD_BM, ZDMC) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ptmtZdjlb = connection.prepareStatement(sqlBlb);
            ptmtZdjlb.setString(1, "" + yptId);
            ptmtZdjlb.setLong(2, mzblId);
            ptmtZdjlb.setInt(3, 1);
            ptmtZdjlb.setString(4, icdbm);
            ptmtZdjlb.setString(5, zdmc);
            int res1 = ptmtZdjlb.executeUpdate();
        }

        // 更新记录
        else {
            log.info("更新诊断记录 {}, {}, {}, {}", yptId, disease, icdbm, zdmc);
            String sqlBlb = "UPDATE MZ_ZDJLB " +
                    "SET ICD_BM=?, ZDMC=? " +
                    "WHERE YPT_ID=?";
            PreparedStatement ptmtBlb = connection.prepareStatement(sqlBlb);
            ptmtBlb.setString(1, icdbm);
            ptmtBlb.setString(2, zdmc);
            ptmtBlb.setString(3, "" + yptId);
            int res2 = ptmtBlb.executeUpdate();
        }
    }


    private void doProcessYwd(Connection connection, Long ghdId, Long mzblId, Integer brId, Long kdks, Long kdys, String zdbm, String zdmc, Map<String, Object> map) throws SQLException {
        // 准备字段
        String yptId = (String) map.get("code");
        Long now = System.currentTimeMillis();
        String ywdId = "" + now;
        java.sql.Date chrq = new java.sql.Date(now);

        Integer ts = 0;

        // 处方类型
        Integer cflx = 0;
        Integer type = (int) map.get("type");
        switch (type) {
            case 1: // 成药
                cflx = 1;
                ts = 1;
                break;
            case 2: // 中药饮片
                cflx = 2;
                ts = (int) map.get("dayCount");
                break;
            case 3: // 中药颗粒剂
                cflx = 2;
                ts = (int) map.get("dayCount");
                break;
            case 4: // 协定方(熏蒸方)
                cflx = 2;
                ts = (int) map.get("dayCount");
                break;
            case 5: // 治疗方
                cflx = 1;
                ts = 1;
                break;
        }

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
            return;
        }

        // 处理处方
        log.info("新增处方 {}, {}, {}, {}, {}, {}, {}", yptId, ghdId, mzblId, ywdId, brId, ts, chrq);
        String sqlYwd = "INSERT INTO " +
                "MZ_YWD (YPT_ID, GHD_ID, MZBL_ID, YWD_ID, BR_ID, CFTS, CFRQ, KDKS, KDYS, CFLX, ZT, DYSKSBH, ZXTBH_ID, ZDBM, ZDMC, BRJSFS_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'A', 4, 6, ?, ?, 55)";
        PreparedStatement ptmtYwd = connection.prepareStatement(sqlYwd);
        ptmtYwd.setString(1, yptId);
        ptmtYwd.setString(2, "" + ghdId);
        ptmtYwd.setLong(3, mzblId);
        ptmtYwd.setString(4, ywdId);
        ptmtYwd.setInt(5, brId);
        ptmtYwd.setInt(6, ts);
        ptmtYwd.setDate(7, chrq);
        ptmtYwd.setLong(8, kdks);
        ptmtYwd.setLong(9, kdys);
        ptmtYwd.setInt(10, cflx);
        ptmtYwd.setString(11, zdbm);
        ptmtYwd.setString(12, zdmc);
        int res1 = ptmtYwd.executeUpdate();
        // log.info("新增处方结果 " + res1);

        // 处理每一味药材（并计算总金额）
        ArrayList<Map<String, Object>> itemList = (ArrayList<Map<String, Object>>) map.get("itemList");
        Double je = 0.0;
        switch (type) {
            case 1: // 成药
                je = doProcessFymxbChengyao(connection, ywdId, ts, itemList);
                break;
            case 2: // 中药饮片
                je = doProcessFymxbZhongyao(connection, ywdId, ts, itemList);
                break;
            case 3: // 中药颗粒剂
                je = doProcessFymxbZhongyao(connection, ywdId, ts, itemList);
                break;
            case 4: // 协定方(熏蒸方)
                je = doProcessFymxbZhongyao(connection, ywdId, ts, itemList);
                break;
            case 5: // 治疗方
                je = doProcessFymxbZhiliao(connection, ywdId, ts, itemList);
                break;
        }

        // 更新业务单金额
        log.info("更新金额 {}, {}", ywdId, je);
        String sqlYwd2 = "" +
                "UPDATE MZ_YWD " +
                "SET CFJE = ? " +
                "WHERE YWD_ID = ?";
        PreparedStatement ptmtYwd2 = connection.prepareStatement(sqlYwd2);
        ptmtYwd2.setDouble(1, je);
        ptmtYwd2.setString(2, ywdId);
        int res2 = ptmtYwd2.executeUpdate();
        // log.info("更新业务单 " + res2);
    }

    private Double doProcessFymxbChengyao(Connection connection, String ywdId, Integer ts, ArrayList<Map<String, Object>> itemList) throws SQLException {
        Integer xh = 1;
        Double totalJe = 0.0;
        for (Map<String, Object> item : itemList) {
            log.info("成药 " + item);
            // 解析字段
            String name = ((String) item.get("name")).trim();
            Integer yl = ((Double) item.get("dosage")).intValue();
            Integer ypggcdId = 0;
            Integer yldw = 0;
            Integer dw = 0;
            Integer dsl = ((Double) item.get("dosage")).intValue();
            Integer sl = (int) item.get("days");
            Double lsjg = 0.0;
            Double dje = 0.0;
            Double je = 0.0;
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
                dje = lsjg * sl;
                je = dje * ts;
                totalJe += je;
                log.info("价格 {}, {}, {}", ypggcdId, lsjg, dje);
            } else {
                log.warn("成药不存在 {}", name);
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
        return totalJe;
    }

    private Double doProcessFymxbZhongyao(Connection connection, String ywdId, Integer ts, ArrayList<Map<String, Object>> itemList) throws SQLException {
        Integer xh = 1;
        Double totalJe = 0.0;
        for (Map<String, Object> item : itemList) {
            log.info("药材 " + item);
            // 解析字段
            String name = ((String) item.get("name")).trim();
            Integer yl = ((Double) item.get("dosage")).intValue();
            Integer ypggcdId = 0;
            Integer yldw = 0;
            Integer dw = mapUnit.getOrDefault(item.get("unit"), 39168);
            Integer dsl = ((Double) item.get("dosage")).intValue();
            Integer sl = ((Double) item.get("dosage")).intValue();
            Double lsjg = 0.0;
            Double dje = 0.0;
            Double je = 0.0;
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
                je = dje * ts;
                totalJe += je;
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
        return totalJe;
    }

    private Double doProcessFymxbZhiliao(Connection connection, String ywdId, Integer ts, ArrayList<Map<String, Object>> itemList) throws SQLException {
        Integer xh = 1;
        Double totalJe = 0.0;
        for (Map<String, Object> item : itemList) {
            log.info("治疗 " + item);
            // 解析字段
            String nameYpt = ((String) item.get("name")).trim();
            Integer yl = ((Double) item.get("quantity")).intValue();
            Integer ypggcdId = 0;
            Integer yldw = 0;
            Integer dw = mapUnit.getOrDefault(item.get("unit"), 39192);
            Integer dsl = ((Double) item.get("quantity")).intValue();
            Integer sl = ((Double) item.get("quantity")).intValue();
            Double lsjg = 0.0;
            Double dje = 0.0;
            Double je = 0.0;
            // 治疗映射
            String nameHis = mapZhiliao.get(nameYpt);
            if (nameHis == null) {
                log.warn("治疗映射不存在 {}", nameYpt);
                continue;
            }

            // 查询治疗价格
            String sqlYp = "" +
                    "SELECT * " +
                    "FROM DM_GGCD A JOIN DM_YPZD B ON A.YPZD_ID = B.YPZD_ID " +
                    "WHERE B.YPTYM like ?";
            PreparedStatement ptmtYp = connection.prepareStatement(sqlYp);
            ptmtYp.setString(1, nameHis);
            ResultSet rsYp = ptmtYp.executeQuery();
            if (rsYp.next()) {
                ypggcdId = rsYp.getInt("YPGGCD_ID");
                lsjg = rsYp.getDouble("JJDWLSJ");
                dje = lsjg * yl;
                je = dje * ts;
                totalJe += je;
                log.info("价格 {}, {}, {}", ypggcdId, lsjg, dje);
            } else {
                log.warn("治疗不存在 {}", nameHis);
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
        return totalJe;
    }
}
