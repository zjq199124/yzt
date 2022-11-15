package com.maizhiyu.yzt.bean.aci;

import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.bean.aro.YptTreatmentRO.AddTreatmentRO;
import com.maizhiyu.yzt.bean.aro.YptTreatmentRO.SetTreatmentRO;
import com.maizhiyu.yzt.bean.avo.YptTreatmentVO.GetTreatmentListVO;
import com.maizhiyu.yzt.bean.avo.YptTreatmentVO.GetTreatmentVO;
import com.maizhiyu.yzt.entity.YptTreatment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-15T09:43:04+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_341 (Oracle Corporation)"
)
public class YptTreatmentCIImpl implements YptTreatmentCI {

    @Override
    public YptTreatment convert(AddTreatmentRO ro) {
        if ( ro == null ) {
            return null;
        }

        YptTreatment yptTreatment = new YptTreatment();

        yptTreatment.setCode( ro.getCode() );
        yptTreatment.setName( ro.getName() );
        yptTreatment.setPinyin( ro.getPinyin() );
        yptTreatment.setAbbr( ro.getAbbr() );
        yptTreatment.setHiscode( ro.getHiscode() );
        yptTreatment.setHisname( ro.getHisname() );

        return yptTreatment;
    }

    @Override
    public YptTreatment convert(SetTreatmentRO ro) {
        if ( ro == null ) {
            return null;
        }

        YptTreatment yptTreatment = new YptTreatment();

        yptTreatment.setId( ro.getId() );
        yptTreatment.setCode( ro.getCode() );
        yptTreatment.setName( ro.getName() );
        yptTreatment.setPinyin( ro.getPinyin() );
        yptTreatment.setAbbr( ro.getAbbr() );
        yptTreatment.setHiscode( ro.getHiscode() );
        yptTreatment.setHisname( ro.getHisname() );

        return yptTreatment;
    }

    @Override
    public GetTreatmentVO invertGetTreatmentVO(YptTreatment treatment) {
        if ( treatment == null ) {
            return null;
        }

        GetTreatmentVO getTreatmentVO = new GetTreatmentVO();

        getTreatmentVO.setId( treatment.getId() );
        getTreatmentVO.setCode( treatment.getCode() );
        getTreatmentVO.setName( treatment.getName() );
        getTreatmentVO.setPinyin( treatment.getPinyin() );
        getTreatmentVO.setAbbr( treatment.getAbbr() );
        getTreatmentVO.setHiscode( treatment.getHiscode() );
        getTreatmentVO.setHisname( treatment.getHisname() );

        return getTreatmentVO;
    }

    @Override
    public PageInfo<GetTreatmentListVO> invertGetTreatmentListVO(PageInfo<YptTreatment> pageInfo) {
        if ( pageInfo == null ) {
            return null;
        }

        PageInfo<GetTreatmentListVO> pageInfo1 = new PageInfo<GetTreatmentListVO>();

        pageInfo1.setPageNum( pageInfo.getPageNum() );
        pageInfo1.setPageSize( pageInfo.getPageSize() );
        pageInfo1.setSize( pageInfo.getSize() );
        pageInfo1.setStartRow( pageInfo.getStartRow() );
        pageInfo1.setEndRow( pageInfo.getEndRow() );
        pageInfo1.setTotal( pageInfo.getTotal() );
        pageInfo1.setPages( pageInfo.getPages() );
        pageInfo1.setList( yptTreatmentListToGetTreatmentListVOList( pageInfo.getList() ) );
        pageInfo1.setFirstPage( pageInfo.getFirstPage() );
        pageInfo1.setPrePage( pageInfo.getPrePage() );
        pageInfo1.setNextPage( pageInfo.getNextPage() );
        pageInfo1.setLastPage( pageInfo.getLastPage() );
        pageInfo1.setIsFirstPage( pageInfo.isIsFirstPage() );
        pageInfo1.setIsLastPage( pageInfo.isIsLastPage() );
        pageInfo1.setHasPreviousPage( pageInfo.isHasPreviousPage() );
        pageInfo1.setHasNextPage( pageInfo.isHasNextPage() );
        pageInfo1.setNavigatePages( pageInfo.getNavigatePages() );
        int[] navigatepageNums = pageInfo.getNavigatepageNums();
        if ( navigatepageNums != null ) {
            pageInfo1.setNavigatepageNums( Arrays.copyOf( navigatepageNums, navigatepageNums.length ) );
        }
        pageInfo1.setNavigateFirstPage( pageInfo.getNavigateFirstPage() );
        pageInfo1.setNavigateLastPage( pageInfo.getNavigateLastPage() );

        return pageInfo1;
    }

    protected GetTreatmentListVO yptTreatmentToGetTreatmentListVO(YptTreatment yptTreatment) {
        if ( yptTreatment == null ) {
            return null;
        }

        GetTreatmentListVO getTreatmentListVO = new GetTreatmentListVO();

        getTreatmentListVO.setId( yptTreatment.getId() );
        getTreatmentListVO.setCode( yptTreatment.getCode() );
        getTreatmentListVO.setName( yptTreatment.getName() );
        getTreatmentListVO.setPinyin( yptTreatment.getPinyin() );
        getTreatmentListVO.setAbbr( yptTreatment.getAbbr() );
        getTreatmentListVO.setHiscode( yptTreatment.getHiscode() );
        getTreatmentListVO.setHisname( yptTreatment.getHisname() );

        return getTreatmentListVO;
    }

    protected List<GetTreatmentListVO> yptTreatmentListToGetTreatmentListVOList(List<YptTreatment> list) {
        if ( list == null ) {
            return null;
        }

        List<GetTreatmentListVO> list1 = new ArrayList<GetTreatmentListVO>( list.size() );
        for ( YptTreatment yptTreatment : list ) {
            list1.add( yptTreatmentToGetTreatmentListVO( yptTreatment ) );
        }

        return list1;
    }
}
