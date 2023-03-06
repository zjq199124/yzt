package com.maizhiyu.yzt.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 分页查询实体类
 * Author zjq
 * Date 2023/2/20
 */
@ApiModel("分页查询实体，对象体内传递pageNum（当前页码）、pageSize（每页条数），默认1页10条")
@Data
public class PageQuery {

    @ApiModelProperty(value = "当前页数", required = false)
    private Long pageNum;

    @ApiModelProperty(value = "每页多少条", required = false)
    private Long pageSize;

    @ApiModelProperty(value = "按照什么属性排序", required = false)
    private List<String> properties;

    @ApiModelProperty(value = "排序方向", required = false)
    private List<String> directions;

    public PageQuery(Long pageNum, Long pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    private PageQuery() {
        this.pageNum = 1L;
        this.pageSize = 10L;
    }

    public Page convertToPage() {
        return new Page(this.pageNum, this.pageSize);
    }

    /**
     * 当使用pageHelper插件排序时，可使用该方法，前端传过来排序规则，该方法可实现多条件组合排序
     */
    public String getOrderBy() {
        String orderBy = "";
        final StringBuilder sb = new StringBuilder();
        if (!CollectionUtils.isEmpty(properties)) {
            if (CollectionUtils.isEmpty(directions)) {
                properties.stream().forEach(pro -> {
                    sb.append(pro + " " + "ASC" + ",");
                });
            } else {
                for (int i = 0; i < properties.size(); i++) {
                    sb.append(properties.get(i) + " ");
                    for (int j = 0; j < directions.size(); j++) {
                        if (i == j) {
                            sb.append(directions.get(j).toUpperCase() + ",");
                            break;
                        }
                    }
                }
            }
            orderBy = sb.deleteCharAt(sb.length() - 1).toString();
        }
        return orderBy;
    }
}

