package com.maizhiyu.yzt.bean.aci;

import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.bean.aro.YptMedicantRO.AddMedicantRO;
import com.maizhiyu.yzt.bean.aro.YptMedicantRO.SetMedicantRO;
import com.maizhiyu.yzt.bean.avo.YptMedicantVO.GetMedicantListVO;
import com.maizhiyu.yzt.bean.avo.YptMedicantVO.GetMedicantVO;
import com.maizhiyu.yzt.entity.YptMedicant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-15T09:43:04+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_341 (Oracle Corporation)"
)
public class YptMedicantCIImpl implements YptMedicantCI {

    @Override
    public YptMedicant convert(AddMedicantRO ro) {
        if ( ro == null ) {
            return null;
        }

        YptMedicant yptMedicant = new YptMedicant();

        yptMedicant.setCode( ro.getCode() );
        yptMedicant.setName( ro.getName() );
        yptMedicant.setPinyin( ro.getPinyin() );
        yptMedicant.setAbbr( ro.getAbbr() );
        yptMedicant.setHiscode( ro.getHiscode() );
        yptMedicant.setHisname( ro.getHisname() );

        return yptMedicant;
    }

    @Override
    public YptMedicant convert(SetMedicantRO ro) {
        if ( ro == null ) {
            return null;
        }

        YptMedicant yptMedicant = new YptMedicant();

        yptMedicant.setId( ro.getId() );
        yptMedicant.setCode( ro.getCode() );
        yptMedicant.setName( ro.getName() );
        yptMedicant.setPinyin( ro.getPinyin() );
        yptMedicant.setAbbr( ro.getAbbr() );
        yptMedicant.setHiscode( ro.getHiscode() );
        yptMedicant.setHisname( ro.getHisname() );

        return yptMedicant;
    }

    @Override
    public GetMedicantVO invertGetMedicantVO(YptMedicant medicant) {
        if ( medicant == null ) {
            return null;
        }

        GetMedicantVO getMedicantVO = new GetMedicantVO();

        getMedicantVO.setId( medicant.getId() );
        getMedicantVO.setCode( medicant.getCode() );
        getMedicantVO.setName( medicant.getName() );
        getMedicantVO.setPinyin( medicant.getPinyin() );
        getMedicantVO.setAbbr( medicant.getAbbr() );
        getMedicantVO.setHiscode( medicant.getHiscode() );
        getMedicantVO.setHisname( medicant.getHisname() );

        return getMedicantVO;
    }

    @Override
    public PageInfo<GetMedicantListVO> invertGetMedicantListVO(PageInfo<YptMedicant> pageInfo) {
        if ( pageInfo == null ) {
            return null;
        }

        PageInfo<GetMedicantListVO> pageInfo1 = new PageInfo<GetMedicantListVO>();

        pageInfo1.setPageNum( pageInfo.getPageNum() );
        pageInfo1.setPageSize( pageInfo.getPageSize() );
        pageInfo1.setSize( pageInfo.getSize() );
        pageInfo1.setStartRow( pageInfo.getStartRow() );
        pageInfo1.setEndRow( pageInfo.getEndRow() );
        pageInfo1.setTotal( pageInfo.getTotal() );
        pageInfo1.setPages( pageInfo.getPages() );
        pageInfo1.setList( yptMedicantListToGetMedicantListVOList( pageInfo.getList() ) );
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

    protected GetMedicantListVO yptMedicantToGetMedicantListVO(YptMedicant yptMedicant) {
        if ( yptMedicant == null ) {
            return null;
        }

        GetMedicantListVO getMedicantListVO = new GetMedicantListVO();

        getMedicantListVO.setId( yptMedicant.getId() );
        getMedicantListVO.setCode( yptMedicant.getCode() );
        getMedicantListVO.setName( yptMedicant.getName() );
        getMedicantListVO.setPinyin( yptMedicant.getPinyin() );
        getMedicantListVO.setAbbr( yptMedicant.getAbbr() );
        getMedicantListVO.setHiscode( yptMedicant.getHiscode() );
        getMedicantListVO.setHisname( yptMedicant.getHisname() );

        return getMedicantListVO;
    }

    protected List<GetMedicantListVO> yptMedicantListToGetMedicantListVOList(List<YptMedicant> list) {
        if ( list == null ) {
            return null;
        }

        List<GetMedicantListVO> list1 = new ArrayList<GetMedicantListVO>( list.size() );
        for ( YptMedicant yptMedicant : list ) {
            list1.add( yptMedicantToGetMedicantListVO( yptMedicant ) );
        }

        return list1;
    }
}
