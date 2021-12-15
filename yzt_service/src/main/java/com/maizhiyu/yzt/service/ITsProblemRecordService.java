package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.TsProblemRecord;

/**
 * className:ITsProblemRecord
 * Package:com.maizhiyu.yzt.service
 * Description:
 *
 * @DATE:2021/12/13 4:21 下午
 * @Author:2101825180@qq.com
 */
public interface ITsProblemRecordService  {
    void addTsProblemRecord(TsProblemRecord tsProblemRecord);

    TsProblemRecord getTsProblemRecordById(Long tsProblemRecordId);

}
