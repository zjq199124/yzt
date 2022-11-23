package com.maizhiyu.yzt.utils.infrared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Anchor {
    /**
     * 起始位与锚点相距行数
     */
    private int startIndex;

    /**
     * 结束位与锚点相距行数
     */
    private int endIndex;

    /**
     * 起始锚点标志
     */
    private String startTarget;

    /**
     * 确定锚点
     */
    private String target;

    /**
     * 结束锚点标志
     */
    private String endTarget;


    public int matchingAnchor(String[] lines) {
        int result = -1;
        //循环每一行文本，进行匹配
        for (int i = 0; i < lines.length; i++) {
            //当前行文本
            String line = lines[i];
            //判断：起始行是否找到，找到则跳过 result.getStartIndex() < 0，
            //判断：匹配锚点
            //问题：确保所有锚点都能匹配成功
            if (result < 0 && line.contains(target) && lines[i + startIndex].contains(startTarget)
                    && lines[i + endIndex].contains(endTarget)) {
                result = i;
                break;
            }
        }
        return result;
    }
}
