package com.maizhiyu.yzt.utils.infrared;

import cn.hutool.core.util.StrUtil;
import com.maizhiyu.yzt.entity.TxInfraredData;
import com.maizhiyu.yzt.entity.TxInfraredDetails;
import com.maizhiyu.yzt.enums.InfraredImageEnum;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * 红外pdf解析
 */
public class InfraredPdfAnalysis {

    public static TxInfraredData getThwbase(String[] lines) {
        //找到传统四诊的位置并以此为锚点
        Anchor anchorctsz = new Anchor(-1, 1, "1", "传统四诊", "望 ");
        String[] user = lines[anchorctsz.matchingAnchor(lines) - 2].replace("岁", "").split(" ");//用户信息
        String watch = lines[anchorctsz.matchingAnchor(lines) + 1].split(" ")[1];
        String listen = lines[anchorctsz.matchingAnchor(lines) + 2].split(" ")[1];
        String ask = lines[anchorctsz.matchingAnchor(lines) + 3].split(" ")[1];
        String fell = lines[anchorctsz.matchingAnchor(lines) + 4].split(" ")[1];
        String DuiChenXing = lines[anchorctsz.matchingAnchor(lines) + 7] + lines[anchorctsz.matchingAnchor(lines) + 8];
        String GuilvXing = lines[anchorctsz.matchingAnchor(lines) + 10] + lines[anchorctsz.matchingAnchor(lines) + 11];
        String BianHanRe = lines[anchorctsz.matchingAnchor(lines) + 13] + lines[anchorctsz.matchingAnchor(lines) + 14];
        String[] TuXiangTeZheng = lines[anchorctsz.matchingAnchor(lines) + 16].split(" ", -1);
        //找到整体评估的位置并以此为锚点
        Anchor anchorztpg = new Anchor(-1, 1, "总体评估", "总体评估", "1. 热态分布：");
        String ReTaiFenBu = lines[anchorztpg.matchingAnchor(lines) + 2].concat(lines[anchorztpg.matchingAnchor(lines) + 3])
                .concat(lines[anchorztpg.matchingAnchor(lines) + 4])
                .concat(lines[anchorztpg.matchingAnchor(lines) + 5]);
        String ZhongyiTiZhi = lines[anchorztpg.matchingAnchor(lines) + 6].split("：")[1];
        String ZangFu = lines[anchorztpg.matchingAnchor(lines) + 7].split("：")[1];
        String SanJiao = lines[anchorztpg.matchingAnchor(lines) + 8].split("：")[1];
        String JingLuo = lines[anchorztpg.matchingAnchor(lines) + 9].split("：")[1];
        String JiePouFenQu = lines[anchorztpg.matchingAnchor(lines) + 10].split("：")[1];
        String LiuJingPiBu = lines[anchorztpg.matchingAnchor(lines) + 11].split("：")[1];
        String KaiQiaoQiHua = lines[anchorztpg.matchingAnchor(lines) + 12].split("：")[1];
        String WuZangSeBu = lines[anchorztpg.matchingAnchor(lines) + 13].split("：")[1];
        String TouBuDuiChen = lines[anchorztpg.matchingAnchor(lines) + 14].split("：")[1];
        String ZhuZheng = lines[anchorztpg.matchingAnchor(lines) + 15].split("：")[1];
        String JianZheng = lines[anchorztpg.matchingAnchor(lines) + 16].split("：")[1];
        Anchor anchortlyz = new Anchor(0, 0, "", "调理建议", "");
        String TiaoLiYuanZe = lines[anchortlyz.matchingAnchor(lines) + 1];
        //考虑到每次的诊疗方案不同，导致行数不确定，这里采用
        //确定每个分支的锚点进行便利，确保读到每个信息
        Anchor anchortjf = new Anchor(0, 0, "", "2. 经方 / 成药", "");
        int jf = anchortjf.matchingAnchor(lines);
        Anchor anchortjl = new Anchor(0, 0, "", "3. 经络 / 穴位", "");
        int jl = anchortjl.matchingAnchor(lines);
        Anchor anchortys = new Anchor(0, 0, "", "4. 饮食 / 药膳", "");
        int ys = anchortys.matchingAnchor(lines);
        Anchor anchortyybj = new Anchor(0, 0, "", "5. 营养保健品", "");
        int yybj = anchortyybj.matchingAnchor(lines);
        //list存放每个部分的信息
        List<String> chengyao = new ArrayList<>();
        List<String> xuewei = new ArrayList<>();
        List<String> yaoshan = new ArrayList<>();
        List<String> yybjp = new ArrayList<>();
        for (int i = jf; i < jl; i++) {
            chengyao.add(lines[i]);
        }
        String ChenYao = String.join(",", chengyao);
        for (int i = jl; i < ys; i++) {
            xuewei.add(lines[i]);
        }
        String XueWei = String.join(",", xuewei);
        for (int i = ys; i < yybj; i++) {
            yaoshan.add(lines[i]);
        }
        String YaoShan = String.join(",", yaoshan);
        for (int i = yybj; i <= lines.length; i++) {
            if (i == lines.length)
                break;
            yybjp.add(lines[i]);
        }
        String YingYangBaoJianPin = String.join(",", yybjp);
        TxInfraredData txInfraredData = new TxInfraredData();
        txInfraredData.setUserName(user[2]);
        txInfraredData.setSex(user[0].equals("男") ? 1 : 0);
//        thwbase.setAge(user[1]);
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        txInfraredData.setTestDate(new Date());
        txInfraredData.setWatch(watch);
        txInfraredData.setListen(listen);
        txInfraredData.setAsk(ask);
        txInfraredData.setFeel(fell);
        txInfraredData.setDuichenxing(DuiChenXing);
        txInfraredData.setTuxiangtezheng(TuXiangTeZheng[1].concat(TuXiangTeZheng[2]).concat(TuXiangTeZheng[3]));
        txInfraredData.setTizhiliangbiao(TuXiangTeZheng.length > 5 ? TuXiangTeZheng[5] : null);
        txInfraredData.setRetaifenbu(ReTaiFenBu);
        txInfraredData.setZhongyitizhi(ZhongyiTiZhi);
        txInfraredData.setZangfu(ZangFu);
        txInfraredData.setSanjiao(SanJiao);
        txInfraredData.setJingluo(JingLuo);
        txInfraredData.setJiepoufenqu(JiePouFenQu);
        txInfraredData.setLiujingpibu(LiuJingPiBu);
        txInfraredData.setKaiqiaoqihua(KaiQiaoQiHua);
        txInfraredData.setWuzangsebu(WuZangSeBu);
        txInfraredData.setToubuduichen(TouBuDuiChen);
        txInfraredData.setZhuzheng(ZhuZheng);
        txInfraredData.setJianzheng(JianZheng);
        txInfraredData.setTiaoliyuanze(TiaoLiYuanZe);
        txInfraredData.setChenyao(ChenYao);
        txInfraredData.setXuewei(XueWei);
        txInfraredData.setYinshi(YaoShan);
        txInfraredData.setBianhanre(BianHanRe);
        txInfraredData.setGuilvxing(GuilvXing);
        txInfraredData.setYingyangbaojianpin(YingYangBaoJianPin);
        return txInfraredData;
    }

    //脏腑
    public static List<TxInfraredDetails> getZangfu(String[] lines, Long id) {
        Anchor anchor1 = new Anchor(-1, 2, "肺", "左 ", "大肠 ");
        Anchor anchor2 = new Anchor(-1, 1, "左 ", "右 ", "三 焦");
        int start = anchor1.matchingAnchor(lines);
        int end = anchor2.matchingAnchor(lines);
        List<TxInfraredDetails> list = new ArrayList<>();
        for (int i = start; i < end + 1; i++) {
            String[] data = lines[i].split(" ");
            TxInfraredDetails t = new TxInfraredDetails();
            if (data.length > 1) {
                t.setInfraredDataId(id);
                if (data[0].equals("左") && lines[i - 1].equals("肺")) {
                    t.setFace("左肺");
                } else if (data[0].equals("右") && lines[i + 1].contains("大肠")) {
                    t.setFace("右肺");
                } else if (data[0].equals("左") && lines[i - 1].equals("肾")) {
                    t.setFace("左肾");
                } else if (data[0].equals("右") && lines[i + 1].equals("三 焦 ℃")) {
                    t.setFace("右肾");
                } else
                    t.setFace(data[0]);
                t.setZuigao(data[1]);
                t.setZuidi(data[2]);
                t.setJicha(data[3]);
                t.setBiaozhuncha(data[4]);
                t.setZhengtaicha(data[5]);
                t.setLeibie("脏腑");
                list.add(t);
            }

        }
        return list;
    }

    //三焦
    public static List<TxInfraredDetails> getSanjiao(String[] lines, Long id) {
        Anchor anchor1 = new Anchor(-1, 1, "分 区 ", "上焦 ", "中焦 ");
        Anchor anchor2 = new Anchor(-1, 1, "中焦 ", "下焦 ", "最高：");
        int start = anchor1.matchingAnchor(lines);
        int end = anchor2.matchingAnchor(lines);
        List<TxInfraredDetails> list = new ArrayList<>();
        for (int i = start; i < end + 1; i++) {
            String[] data = lines[i].split(" ");
            TxInfraredDetails t = new TxInfraredDetails();
            if (data.length > 1) {
                t.setInfraredDataId(id);
                t.setFace(data[0].equals("上焦") ? "上焦" : data[0]);
                t.setZuigao(data[1]);
                t.setZuidi(data[2]);
                t.setJicha(data[3]);
                t.setBiaozhuncha(data[4]);
                t.setZhengtaicha(data[5]);
                t.setLeibie("三焦");
                list.add(t);
            }

        }
        return list;
    }

    //解剖分取正侧
    public static List<TxInfraredDetails> getZJiepaofenqu(String[] lines, Long id) {
        Anchor anchor1 = new Anchor(-1, 1, "正 面 ", "胸膺 ", "左胁 ");
        Anchor anchor2 = new Anchor(-1, 1, "左少腹 ", "右少腹 ", "背 面 ");
        int start = anchor1.matchingAnchor(lines);
        int end = anchor2.matchingAnchor(lines);
        List<TxInfraredDetails> list = new ArrayList<>();
        for (int i = start; i < end + 1; i++) {
            String[] data = lines[i].split(" ");
            TxInfraredDetails t = new TxInfraredDetails();
            if (data.length > 1) {
                t.setInfraredDataId(id);
                t.setFace(data[0].equals("胸膺") ? "正面胸膺" : "正面" + data[0]);
                t.setZuigao(data[1]);
                t.setZuidi(data[2]);
                t.setJicha(data[3]);
                t.setBiaozhuncha(data[4]);
                t.setZhengtaicha(data[5]);
                t.setLeibie("解剖分区");
                list.add(t);
            }

        }
        return list;
    }

    //解剖分取背面
    public static List<TxInfraredDetails> getBJiepaofenqu(String[] lines, Long id) {
        Anchor anchor1 = new Anchor(-1, 1, "背 面 ", "上胸段 ", "下胸段 ");
        Anchor anchor2 = new Anchor(-1, 1, "肝区 ", "肝对照 ", "图像质量 分");
        int start = anchor1.matchingAnchor(lines);
        int end = anchor2.matchingAnchor(lines);
        List<TxInfraredDetails> list = new ArrayList<>();
        for (int i = start; i < end + 1; i++) {
            String[] data = lines[i].split(" ");
            TxInfraredDetails t = new TxInfraredDetails();
            if (data.length > 1) {
                t.setInfraredDataId(id);
                t.setFace(data[0].equals("上胸段") ? "背面上胸段" : "背面" + data[0]);
                t.setZuigao(data[1]);
                t.setZuidi(data[2]);
                t.setJicha(data[3]);
                t.setBiaozhuncha(data[4]);
                t.setZhengtaicha(data[5]);
                t.setLeibie("解剖分区");
                list.add(t);
            }

        }
        return list;
    }

    //解剖分区左右侧
    public static List<TxInfraredDetails> getZYJiepaofenqu(String[] lines, Long id) {
        Anchor anchor1 = new Anchor(-1, 1, "左右侧 ", "左一区 ", "左二区 ");
        Anchor anchor2 = new Anchor(-1, 1, "右三区 ", "右侧胁 ", "1.胸膺 ");
        int start = anchor1.matchingAnchor(lines);
        int end = anchor2.matchingAnchor(lines);
        List<TxInfraredDetails> list = new ArrayList<>();
        for (int i = start; i < end + 1; i++) {
            String[] data = lines[i].split(" ");
            TxInfraredDetails t = new TxInfraredDetails();
            if (data.length > 1) {
                t.setInfraredDataId(id);
                t.setFace(data[0].equals("左一区") ? "左一区" : data[0]);
                t.setZuigao(data[1]);
                t.setZuidi(data[2]);
                t.setJicha(data[3]);
                t.setBiaozhuncha(data[4]);
                t.setZhengtaicha(data[5]);
                t.setLeibie("解剖分区");
                list.add(t);
            }

        }
        return list;
    }

    //少阴皮部左侧
    public static List<TxInfraredDetails> getZShaoyangpibu(String[] lines, Long id) {
        Anchor anchor1 = new Anchor(-2, 2, "左 侧", "少阳一 ", "少阳二 ");
        Anchor anchor2 = new Anchor(-1, 1, "少阳三 ", "少阳四 ", "右 侧");
        int start = anchor1.matchingAnchor(lines);
        int end = anchor2.matchingAnchor(lines);
        List<TxInfraredDetails> list = new ArrayList<>();
        for (int i = start; i < end + 1; i++) {
            String[] data = lines[i].split(" ");
            TxInfraredDetails t = new TxInfraredDetails();
            if (data.length > 1) {
                t.setInfraredDataId(id);
                t.setFace(data[0].equals("少阳一") ? "左侧少阳一" : "左侧" + data[0]);
                t.setZuigao(data[1]);
                t.setZuidi(data[2]);
                t.setJicha(data[3]);
                t.setBiaozhuncha(data[4]);
                t.setLeibie("少阳皮部");
                list.add(t);
            }

        }
        return list;
    }

    //少阴皮部右侧
    public static List<TxInfraredDetails> getYShaoyangpibu(String[] lines, Long id) {
        Anchor anchor1 = new Anchor(-2, 1, "右 侧", "少阳一 ", "少阳二 ");
        Anchor anchor2 = new Anchor(-1, 1, "少阳三 ", "少阳四 ", "太阳皮部");
        int start = anchor1.matchingAnchor(lines);
        int end = anchor2.matchingAnchor(lines);
        List<TxInfraredDetails> list = new ArrayList<>();
        for (int i = start; i < end + 1; i++) {
            String[] data = lines[i].split(" ");
            TxInfraredDetails t = new TxInfraredDetails();
            if (data.length > 1) {
                t.setInfraredDataId(id);
                t.setFace(data[0].equals("少阳一") ? "右侧少阳一" : "右侧" + data[0]);
                t.setZuigao(data[1]);
                t.setZuidi(data[2]);
                t.setJicha(data[3]);
                t.setBiaozhuncha(data[4]);
                t.setLeibie("少阳皮部");
                list.add(t);
            }

        }
        return list;
    }

    //太阴皮部左右侧
    public static List<TxInfraredDetails> getZTaiyangpibu(String[] lines, Long id) {
        Anchor anchor1 = new Anchor(-1, 2, "橙色区域 ", "左 侧 ", "右 侧 ");
        Anchor anchor2 = new Anchor(-2, 1, "左 侧 ", "右 侧 ", "阳明皮部 ℃");
        int start = anchor1.matchingAnchor(lines);
        int end = anchor2.matchingAnchor(lines);
        List<TxInfraredDetails> list = new ArrayList<>();
        for (int i = start; i < end + 1; i++) {
            String[] data = lines[i].split(" ");
            TxInfraredDetails t = new TxInfraredDetails();
            if (data.length > 1) {
                t.setInfraredDataId(id);
                t.setFace(data[0].equals("左") ? "左侧" : data[0] + "侧");
                t.setZuigao(data[2]);
                t.setZuidi(data[3]);
                t.setJicha(data[4]);
                t.setBiaozhuncha(data[5]);
                t.setLeibie("太阳皮部");
                list.add(t);
            }

        }
        return list;
    }

    //阳明皮部左侧
    public static List<TxInfraredDetails> getZYangmingpibu(String[] lines, Long id) {
        Anchor anchor1 = new Anchor(-2, 2, "左 侧", "阳明一 ", "阳明二 ");
        Anchor anchor2 = new Anchor(-1, 1, "阳明二 ", "阳明三 ", "右 侧");
        int start = anchor1.matchingAnchor(lines);
        int end = anchor2.matchingAnchor(lines);
        List<TxInfraredDetails> list = new ArrayList<>();
        for (int i = start; i < end + 1; i++) {
            String[] data = lines[i].split(" ");
            TxInfraredDetails t = new TxInfraredDetails();
            if (data.length > 1) {
                t.setInfraredDataId(id);
                t.setFace(data[0].equals("阳明一") ? "左侧阳明一" : "左侧" + data[0]);
                t.setZuigao(data[1]);
                t.setZuidi(data[2]);
                t.setJicha(data[3]);
                t.setBiaozhuncha(data[4]);
                t.setLeibie("阳明皮部");
                list.add(t);
            }

        }
        return list;
    }

    //阳明皮部右侧
    public static List<TxInfraredDetails> getYYangmingpibu(String[] lines, Long id) {
        Anchor anchor1 = new Anchor(-2, 1, "右 侧", "阳明一 ", "阳明二 ");
        Anchor anchor2 = new Anchor(-1, 1, "阳明二 ", "阳明三 ", "图像质量 分");
        int start = anchor1.matchingAnchor(lines);
        int end = anchor2.matchingAnchor(lines);
        List<TxInfraredDetails> list = new ArrayList<>();
        for (int i = start; i < end + 1; i++) {
            String[] data = lines[i].split(" ");
            TxInfraredDetails t = new TxInfraredDetails();
            if (data.length > 1) {
                t.setInfraredDataId(id);
                t.setFace(data[0].equals("阳明一") ? "右侧阳明一" : "右侧" + data[0]);
                t.setZuigao(data[1]);
                t.setZuidi(data[2]);
                t.setJicha(data[3]);
                t.setBiaozhuncha(data[4]);
                t.setLeibie("阳明皮部");
                list.add(t);
            }

        }
        return list;
    }

    //太阴皮部左侧
    public static List<TxInfraredDetails> getZTaiyinpibu(String[] lines, Long id) {
        Anchor anchor1 = new Anchor(-2, 2, "左 侧", "太阴一 ", "太阴二 ");
        Anchor anchor2 = new Anchor(-2, 1, "太阴一 ", "太阴二 ", "右 侧");
        int start = anchor1.matchingAnchor(lines);
        int end = anchor2.matchingAnchor(lines);
        List<TxInfraredDetails> list = new ArrayList<>();
        for (int i = start; i < end + 1; i++) {
            String[] data = lines[i].split(" ");
            TxInfraredDetails t = new TxInfraredDetails();
            if (data.length > 1) {
                t.setInfraredDataId(id);
                t.setFace(data[0].equals("太阴一 ") ? "左侧太阴一 " : "左侧" + data[0]);
                t.setZuigao(data[1]);
                t.setZuidi(data[2]);
                t.setJicha(data[3]);
                t.setBiaozhuncha(data[4]);
                t.setLeibie("太阴皮部");
                list.add(t);
            }

        }
        return list;
    }

    //太阴皮部右侧
    public static List<TxInfraredDetails> getYTaiyinpibu(String[] lines, Long id) {
        Anchor anchor1 = new Anchor(-2, 1, "右 侧", "太阴一 ", "太阴二 ");
        Anchor anchor2 = new Anchor(-1, 1, "太阴一 ", "太阴二 ", "少阴皮部 ℃");
        int start = anchor1.matchingAnchor(lines);
        int end = anchor2.matchingAnchor(lines);
        List<TxInfraredDetails> list = new ArrayList<>();
        for (int i = start; i < end + 1; i++) {
            String[] data = lines[i].split(" ");
            TxInfraredDetails t = new TxInfraredDetails();
            if (data.length > 1) {
                t.setInfraredDataId(id);
                t.setFace(data[0].equals("太阴一 ") ? "右侧太阴一 " : "右侧" + data[0]);
                t.setZuigao(data[1]);
                t.setZuidi(data[2]);
                t.setJicha(data[3]);
                t.setBiaozhuncha(data[4]);
                t.setLeibie("太阴皮部");
                list.add(t);
            }

        }
        return list;
    }

    //少阴皮部左侧
    public static List<TxInfraredDetails> getZShaoyinpibu(String[] lines, Long id) {
        Anchor anchor1 = new Anchor(-2, 2, "左 侧", "少阴一 ", "少阴二 ");
        Anchor anchor2 = new Anchor(-1, 1, "少阴三 ", "少阴四 ", "右 侧");
        int start = anchor1.matchingAnchor(lines);
        int end = anchor2.matchingAnchor(lines);
        List<TxInfraredDetails> list = new ArrayList<>();
        for (int i = start; i < end + 1; i++) {
            String[] data = lines[i].split(" ");
            TxInfraredDetails t = new TxInfraredDetails();
            if (data.length > 1) {
                t.setInfraredDataId(id);
                t.setFace(data[0].equals("少阴一 ") ? "左侧少阴一 " : "左侧" + data[0]);
                t.setZuigao(data[1]);
                t.setZuidi(data[2]);
                t.setJicha(data[3]);
                t.setBiaozhuncha(data[4]);
                t.setLeibie("少阴皮部");
                list.add(t);
            }

        }
        return list;
    }

    //少阴皮部右侧
    public static List<TxInfraredDetails> getYShaoyinpibu(String[] lines, Long id) {
        Anchor anchor1 = new Anchor(-2, 1, "右 侧", "少阴一 ", "少阴二 ");
        Anchor anchor2 = new Anchor(-1, 1, "少阴三 ", "少阴四 ", "厥阴皮部 ℃");
        int start = anchor1.matchingAnchor(lines);
        int end = anchor2.matchingAnchor(lines);
        List<TxInfraredDetails> list = new ArrayList<>();
        for (int i = start; i < end + 1; i++) {
            String[] data = lines[i].split(" ");
            TxInfraredDetails t = new TxInfraredDetails();
            if (data.length > 1) {
                t.setInfraredDataId(id);
                t.setFace(data[0].equals("少阴一 ") ? "右侧少阴一 " : "右侧" + data[0]);
                t.setZuigao(data[1]);
                t.setZuidi(data[2]);
                t.setJicha(data[3]);
                t.setBiaozhuncha(data[4]);
                t.setLeibie("少阴皮部");
                list.add(t);
            }

        }
        return list;
    }

    //厥阴皮部左侧
    public static List<TxInfraredDetails> getZJueyinpibu(String[] lines, Long id) {
        Anchor anchor1 = new Anchor(-2, 2, "左 侧", "厥阴一 ", "厥阴二 ");
        Anchor anchor2 = new Anchor(-1, 1, "厥阴二 ", "厥阴三 ", "右 侧");
        int start = anchor1.matchingAnchor(lines);
        int end = anchor2.matchingAnchor(lines);
        List<TxInfraredDetails> list = new ArrayList<>();
        for (int i = start; i < end + 1; i++) {
            String[] data = lines[i].split(" ");
            TxInfraredDetails t = new TxInfraredDetails();
            if (data.length > 1) {
                t.setInfraredDataId(id);
                t.setFace(data[0].equals("厥阴一 ") ? "左侧厥阴一 " : "左侧" + data[0]);
                t.setZuigao(data[1]);
                t.setZuidi(data[2]);
                t.setJicha(data[3]);
                t.setBiaozhuncha(data[4]);
                t.setLeibie("厥阴皮部");
                list.add(t);
            }

        }
        return list;
    }

    //厥阴皮部右侧
    public static List<TxInfraredDetails> getYJueyinpibu(String[] lines, Long id) {
        Anchor anchor1 = new Anchor(-2, 1, "右 侧", "厥阴一 ", "厥阴二 ");
        Anchor anchor2 = new Anchor(-1, 1, "厥阴二 ", "厥阴三 ", "图像质量 分");
        int start = anchor1.matchingAnchor(lines);
        int end = anchor2.matchingAnchor(lines);
        List<TxInfraredDetails> list = new ArrayList<>();
        for (int i = start; i < end + 1; i++) {
            String[] data = lines[i].split(" ");
            TxInfraredDetails t = new TxInfraredDetails();
            if (data.length > 1) {
                t.setInfraredDataId(id);
                t.setFace(data[0].equals("厥阴一 ") ? "右侧厥阴一 " : "右侧" + data[0]);
                t.setZuigao(data[1]);
                t.setZuidi(data[2]);
                t.setJicha(data[3]);
                t.setBiaozhuncha(data[4]);
                t.setLeibie("厥阴皮部");
                list.add(t);
            }

        }
        return list;
    }

    //图片获取
    public static List<Map<String, Object>> getImage(PDDocument document) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            for (int i = 0; i < document.getNumberOfPages(); i++) {
                PDResources pdResources = document.getPage(i).getResources();
                int j = 0;
                for (COSName csName : pdResources.getXObjectNames()) {
                    PDXObject pdxObject = pdResources.getXObject(csName);
                    PDStream pdStream = pdxObject.getStream();
                    PDImageXObject image = new PDImageXObject(pdStream, pdResources);
                    String num = i + "-" + j;
                    String type = InfraredImageEnum.getName(num);
                    if (!StrUtil.isEmpty(type)) {
                        BufferedImage imageStream = image.getImage();
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        ImageIO.write(imageStream, "png", os);
                        ByteArrayInputStream inputStream = new ByteArrayInputStream(os.toByteArray());
                        Map<String, Object> resultMap = new HashMap<>();
                        resultMap.put("file", inputStream);
                        resultMap.put("type", type);
                        list.add(resultMap);
                    }
                    j++;
                }
            }
            document.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {

    }

}
