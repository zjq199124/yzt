package com.maizhiyu.yzt.bean.aci;

import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.bean.aro.YptDiseaseRO.AddDiseaseRO;
import com.maizhiyu.yzt.bean.aro.YptDiseaseRO.SetDiseaseRO;
import com.maizhiyu.yzt.bean.avo.YptDiseaseVO.GetDiseaseListVO;
import com.maizhiyu.yzt.bean.avo.YptDiseaseVO.GetDiseaseVO;
import com.maizhiyu.yzt.entity.YptDisease;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-15T09:43:04+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_341 (Oracle Corporation)"
)
public class YptDiseaseCIImpl implements YptDiseaseCI {

    @Override
    public YptDisease convert(AddDiseaseRO ro) {
        if ( ro == null ) {
            return null;
        }

        YptDisease yptDisease = new YptDisease();

        yptDisease.setCode( ro.getCode() );
        yptDisease.setName( ro.getName() );
        yptDisease.setPinyin( ro.getPinyin() );
        yptDisease.setAbbr( ro.getAbbr() );
        yptDisease.setHiscode( ro.getHiscode() );
        yptDisease.setHisname( ro.getHisname() );

        return yptDisease;
    }

    @Override
    public YptDisease convert(SetDiseaseRO ro) {
        if ( ro == null ) {
            return null;
        }

        YptDisease yptDisease = new YptDisease();

        yptDisease.setId( ro.getId() );
        yptDisease.setCode( ro.getCode() );
        yptDisease.setName( ro.getName() );
        yptDisease.setPinyin( ro.getPinyin() );
        yptDisease.setAbbr( ro.getAbbr() );
        yptDisease.setHiscode( ro.getHiscode() );
        yptDisease.setHisname( ro.getHisname() );

        return yptDisease;
    }

    @Override
    public GetDiseaseVO invertGetDiseaseVO(YptDisease disease) {
        if ( disease == null ) {
            return null;
        }

        GetDiseaseVO getDiseaseVO = new GetDiseaseVO();

        getDiseaseVO.setId( disease.getId() );
        getDiseaseVO.setCode( disease.getCode() );
        getDiseaseVO.setName( disease.getName() );
        getDiseaseVO.setPinyin( disease.getPinyin() );
        getDiseaseVO.setAbbr( disease.getAbbr() );
        getDiseaseVO.setHiscode( disease.getHiscode() );
        getDiseaseVO.setHisname( disease.getHisname() );

        return getDiseaseVO;
    }

    @Override
    public PageInfo<GetDiseaseListVO> invertGetDiseaseListVO(PageInfo<YptDisease> pageInfo) {
        if ( pageInfo == null ) {
            return null;
        }

        PageInfo<GetDiseaseListVO> pageInfo1 = new PageInfo<GetDiseaseListVO>();

        pageInfo1.setPageNum( pageInfo.getPageNum() );
        pageInfo1.setPageSize( pageInfo.getPageSize() );
        pageInfo1.setSize( pageInfo.getSize() );
        pageInfo1.setStartRow( pageInfo.getStartRow() );
        pageInfo1.setEndRow( pageInfo.getEndRow() );
        pageInfo1.setTotal( pageInfo.getTotal() );
        pageInfo1.setPages( pageInfo.getPages() );
        pageInfo1.setList( yptDiseaseListToGetDiseaseListVOList( pageInfo.getList() ) );
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

    protected GetDiseaseListVO yptDiseaseToGetDiseaseListVO(YptDisease yptDisease) {
        if ( yptDisease == null ) {
            return null;
        }

        GetDiseaseListVO getDiseaseListVO = new GetDiseaseListVO();

        getDiseaseListVO.setId( yptDisease.getId() );
        getDiseaseListVO.setCode( yptDisease.getCode() );
        getDiseaseListVO.setName( yptDisease.getName() );
        getDiseaseListVO.setPinyin( yptDisease.getPinyin() );
        getDiseaseListVO.setAbbr( yptDisease.getAbbr() );
        getDiseaseListVO.setHiscode( yptDisease.getHiscode() );
        getDiseaseListVO.setHisname( yptDisease.getHisname() );

        return getDiseaseListVO;
    }

    protected List<GetDiseaseListVO> yptDiseaseListToGetDiseaseListVOList(List<YptDisease> list) {
        if ( list == null ) {
            return null;
        }

        List<GetDiseaseListVO> list1 = new ArrayList<GetDiseaseListVO>( list.size() );
        for ( YptDisease yptDisease : list ) {
            list1.add( yptDiseaseToGetDiseaseListVO( yptDisease ) );
        }

        return list1;
    }
}
