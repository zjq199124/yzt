import pymysql
import traceback
import datetime


# MYSQL_HOST = "139.196.153.111"
# MYSQL_USER = "root"
# MYSQL_PSWD = "MZY1qaz@WSX"
# MYSQL_DB   = "yizhentang"

MYSQL_HOST = "121.196.199.219"
MYSQL_USER = "work"
MYSQL_PSWD = "DB1qaz@WSX"
MYSQL_DB   = "yizhentang"


class MysqlTool():

    connect = None
    cursor = None

    def __init__(self):
        pass


    def open(self):
        self.connect = pymysql.connect(MYSQL_HOST, MYSQL_USER, MYSQL_PSWD, MYSQL_DB)
        self.cursor = self.connect.cursor()


    def close(self):
        self.cursor.close()
        self.connect.close()


    def _insert(self, sql):
        try:
            print(sql)
            self.cursor.execute(sql)
            self.connect.commit()
        except Exception as e:
            self.connect.rollback()
            traceback.print_exc()
            print(e)


    def _select(self, sql):
        try:
            print(sql)
            res = self.cursor.execute(sql)
            arr = self.cursor.fetchall()
            return arr
        except Exception as e:
            print(e)


    def insertMsAgency(self, id, status, name, address, contacts, descrip):
        # 判断是否重复
        sql = "select * from ms_agency " \
              "where name = '%s'" \
              % (name)
        res = self._select(sql)
        if len(res) > 0:
            print("【重复数据】", id, name, address)
            return

        dt = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')

        # 不重复则新增
        if id is not None:
            sql = "insert into ms_agency " \
                  "(id, status, name, address, contacts, descrip, update_time, create_time) " \
                  "values (%d, %d, '%s', '%s', '%s', '%s', '%s', '%s')" \
                  % (id, status, name, address, contacts, descrip, dt, dt)
        else:
            sql = "insert into ms_agency " \
                  "(status, name, address, contacts, descrip, update_time, create_time) " \
                  "values (%d, %d, '%s', '%s', '%s', '%s', '%s', '%s')" \
                  % (status, name, address, contacts, descrip, dt, dt)

        # 新增数据
        self._insert(sql)


    def insertMsCustomer(self, agency_name, id, status, wxswitch, name, address, contacts, descrip):
        # 判断是否重复
        sql = "select * from ms_customer " \
              "where name = '%s'" \
              % (name)
        res = self._select(sql)
        if len(res) > 0:
            print("【重复数据】", id, name, address)
            return

        # 查询代理商ID
        sql = "select id from ms_agency " \
              "where name = '%s'" \
              % (agency_name)
        res = self._select(sql)
        if len(res) <= 0:
            print("【代理商不存在】", agency_name, name)
            return

        agency_id = res[0][0]
        dt = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')

        # 不重复则新增
        if id is not None:
            sql = "insert into ms_customer " \
                  "(id, agency_id, status, wxswitch, name, address, contacts, descrip, update_time, create_time) " \
                  "values (%d, %d, %d, %d, '%s', '%s', '%s', '%s', '%s', '%s')" \
                  % (id, agency_id, status, wxswitch, name, address, contacts, descrip, dt, dt)
        else:
            sql = "insert into ms_customer " \
                  "(agency_id, status, wxswitch, name, address, contacts, descrip, update_time, create_time) " \
                  "values (%d, %d, %d, '%s', '%s', '%s', '%s', '%s', '%s')" \
                  % (agency_id, status, wxswitch, name, address, contacts, descrip, dt, dt)

        # 新增数据
        self._insert(sql)


    def insertTeModel(self, id, status, type, name, mfrs, descrip):
        # 判断是否重复
        sql = "select * from te_model " \
              "where name = '%s'" \
              % (name)
        res = self._select(sql)
        if len(res) > 0:
            print("【重复数据】", id, name, mfrs)
            return

        # 不重复则新增
        if id is not None:
            sql = "insert into te_model " \
                  "(id, status, type, name, mfrs, descrip) " \
                  "values (%d, %d, %d, '%s', '%s', '%s')" \
                  % (id, status, type, name, mfrs, descrip)
        else:
            sql = "insert into te_model " \
                  "(status, type, name, mfrs, descrip) " \
                  "values (%d, %d, '%s', '%s', '%s')" \
                  % (status, type, name, mfrs, descrip)

        # 新增数据
        self._insert(sql)


    def insertTeEquip(self, agency_name, customer_name, type_name, model_name, id, status, code, warranty, buy_time):
        # 判断是否重复
        sql = "select * from te_equip " \
              "where code = '%s'" \
              % (code)
        res = self._select(sql)
        if len(res) > 0:
            print("【重复数据】", code)
            return

        # 查询代理商id
        sql = "select agency_id, id from ms_customer " \
              "where name = '%s'" \
              % (customer_name)
        res = self._select(sql)
        if len(res) <= 0:
            print("【客户不存在】", customer_name)
            return

        agency_id = res[0][0]
        customer_id = res[0][1]

        # 查询型号id
        sql = "select id from te_model " \
              "where name = '%s'" \
              % (model_name)
        res = self._select(sql)
        if len(res) <= 0:
            print("【型号不存在】", model_name)
            return

        type = 1
        model_id = res[0][0]

        # 不重复则新增
        if id is not None:
            sql = "insert into te_equip " \
                  "(id, type, agency_id, customer_id, model_id, status, code, warranty, buy_time, update_time, create_time) " \
                  "values (%d, %d, %d, %d, %d, %d, '%s', %d, '%s', '%s', '%s')" \
                  % (id, type, agency_id, customer_id, model_id, status, code, warranty, buy_time, buy_time, buy_time)
        else:
            sql = "insert into te_equip " \
                  "(type, agency_id, customer_id, model_id, status, code, warranty, buy_time, update_time, create_time) " \
                  "values (%d, %d, %d, %d, %d, %d, '%s', %d, '%s', '%s', '%s')" \
                  % (type, agency_id, customer_id, model_id, status, code, warranty, buy_time, buy_time, buy_time)

        # 新增数据
        self._insert(sql)


    def insertTsSytech(self, id, status, name, url, descrip):
        # 判断是否重复
        sql = "select * from ts_sytech " \
              "where name = '%s'" \
              % (name)
        res = self._select(sql)
        if len(res) > 0:
            print("【重复数据】", name)
            return

        # 不重复则新增
        if id is not None:
            sql = "insert into ts_sytech " \
                  "(id, status, name, url, descrip) " \
                  "values (%d, %d, '%s', '%s', '%s')" \
                  % (id, status, name, url, descrip)
        else:
            sql = "insert into ts_sytech " \
                  "(status, name, url, descrip) " \
                  "values (%d, '%s', '%s', '%s')" \
                  % (status, name, url, descrip)

        # 新增数据
        self._insert(sql)


    def insertTsStandard(self, id, tid, sort, name, content):
        # 判断是否重复
        sql = "select * from ts_standard " \
              "where tid = %d and name = '%s'" \
              % (tid, name)
        res = self._select(sql)
        if len(res) > 0:
            print("【重复数据】", tid, name)
            return

        # 不重复则新增
        if id is not None:
            sql = "insert into ts_standard " \
                  "(id, tid, sort, name, content) " \
                  "values (%d, %d, %d, '%s', '%s')" \
                  % (id, tid, sort, name, content)
        else:
            sql = "insert into ts_standard " \
                  "(tid, sort, name, content) " \
                  "values (%d, %d, '%s', '%s')" \
                  % (tid, sort, name, content)

        # 新增数据
        self._insert(sql)


    def insertDictSymptom(self, status, cate, content, note):
        # 判断是否重复
        sql = "select * from dict_symptom " \
              "where content = '%s'" \
              % (content)
        res = self._select(sql)
        if len(res) > 0:
            print("【重复数据】", content)
            return

        # 新增数据
        sql = "insert into dict_symptom " \
              "(status, cate, content, note) " \
              "values (%d, %d, '%s', '%s')" \
              % (status, cate, content, note)
        self._insert(sql)


    def insertDictDisease(self, status, value, content, note):
        # 判断是否重复
        sql = "select * from dict_disease " \
              "where content = '%s'" \
              % (content)
        res = self._select(sql)
        if len(res) > 0:
            print("【重复数据】", content)
            return

        # 新增数据
        # sql = "insert into dict_disease " \
        #       "(status, value, content, note) " \
        #       "values (%d, %d, '%s', '%s')" \
        #       % (status, value, content, note)
        sql = "insert into dict_disease " \
              "(status, content, note) " \
              "values (%d, '%s', '%s')" \
              % (status, content, note)
        self._insert(sql)


    def insertScySyndrome(self, status, disease_name, syndrome_name, symptoms):
        # 查询疾病ID
        disease_id = self._select_disease_id(disease_name)
        if disease_id is None:
            print("【疾病不存在】", disease_name, syndrome_name)
            return

        # 判断是否重复
        sql = "select * from syn_syndrome " \
              "where disease_id = '%s' and name = '%s' " \
              % (disease_id, syndrome_name)
        res = self._select(sql)
        if res is not None and len(res) > 0:
            print("【重复数据】", disease_name, syndrome_name)
            return

        # 新增数据
        sql = "insert into syn_syndrome " \
              "(status, disease_id, name, symptoms) " \
              "values (%d, %d, '%s', '%s')" \
              % (status, disease_id, syndrome_name, symptoms)
        self._insert(sql)


    def insertSchZhongyao(self, status, disease_name, syndrome_name, name, component):
        # 查询疾病ID
        disease_id = self._select_disease_id(disease_name)
        if disease_id is None:
            print("【疾病不存在】", disease_name, syndrome_name, name)
            return

        # 查询辨证ID
        syndrome_id = self._select_syndrome_id(disease_id, syndrome_name)
        if syndrome_id is None:
            print("【辨证不存在】", disease_name, syndrome_name, name)
            return

        # 判断是否重复
        sql = "select * from sch_zhongyao " \
              "where disease_id = %d and syndrome_id = %d and `name` = '%s'" \
              % (disease_id, syndrome_id, name)
        res = self._select(sql)
        if len(res) > 0:
            print("【重复数据】", disease_name, syndrome_name, name)
            return

        # 插入数据
        sql = "insert into sch_zhongyao " \
              "(status, disease_id, syndrome_id, name, component) " \
              "values (%d, %d, %d, '%s', '%s')" \
              % (status, disease_id, syndrome_id, name, component)
        self._insert(sql)


    def insertSchChengyao(self, status, disease_name, syndrome_name, name, component, function, contrain, attention):
        # 查询疾病ID
        disease_id = self._select_disease_id(disease_name)
        if disease_id is None:
            print("【疾病不存在】", disease_name, syndrome_name, name)
            return

        # 查询辨证ID
        syndrome_id = self._select_syndrome_id(disease_id, syndrome_name)
        if syndrome_id is None:
            print("【辨证不存在】", disease_name, syndrome_name, name)
            return

        # 判断是否重复
        sql = "select * from sch_chengyao " \
              "where disease_id = %d and syndrome_id = %d and `name` = '%s'" \
              % (disease_id, syndrome_id, name)
        res = self._select(sql)
        if len(res) > 0:
            print("【重复数据】", disease_name, syndrome_name, name)
            return

        # 新增数据
        sql = "insert into sch_chengyao " \
              "(status, disease_id, syndrome_id, name, component, `function`, contrain, attention) " \
              "values (%d, %d, %d, '%s', '%s', '%s', '%s', '%s')" \
              % (status, disease_id, syndrome_id, name, component, function, contrain, attention)
        self._insert(sql)


    def insertSchXieding(self, status, disease_name, syndrome_name, name, component):
        # 查询疾病ID
        disease_id = self._select_disease_id(disease_name)
        if disease_id is None:
            print("【疾病不存在】", disease_name, syndrome_name, name)
            return

        # 查询辨证ID
        syndrome_id = self._select_syndrome_id(disease_id, syndrome_name)
        if syndrome_id is None:
            print("【辨证不存在】", disease_name, syndrome_name, name)
            return

        # 判断是否重复
        sql = "select * from sch_xieding " \
              "where disease_id = %d and syndrome_id = %d and `name` = '%s'" \
              % (disease_id, syndrome_id, name)
        res = self._select(sql)
        if len(res) > 0:
            print("【重复数据】", disease_name, syndrome_name, name)
            return

        # 新增数据
        sql = "insert into sch_xieding " \
              "(status, disease_id, syndrome_id, name, component) " \
              "values (%d, %d, %d, '%s', '%s')" \
              % (status, disease_id, syndrome_id, name, component)
        self._insert(sql)


    def insertSchSytech(self, status, disease_name, syndrome_name, sytech_name, detail, operation):
        # 查询疾病ID
        disease_id = self._select_disease_id(disease_name)
        if disease_id is None:
            print("【疾病不存在】", disease_name, syndrome_name, sytech_name)
            return

        # 查询辨证ID
        syndrome_id = self._select_syndrome_id(disease_id, syndrome_name)
        if syndrome_id is None:
            print("【辨证不存在】", disease_name, syndrome_name, sytech_name)
            return

        # 查询技术ID
        sql = "select id from ts_sytech " \
              "where name = '%s'" \
              % (sytech_name)
        res = self._select(sql)
        if len(res) <= 0:
            print("【技术不存在】", disease_name, syndrome_name, sytech_name)
            return
        sytech_id = res[0][0]

        # 判断是否重复
        sql = "select * from sch_sytech " \
              "where disease_id = %d and syndrome_id = %d and sytech_id = %d" \
              % (disease_id, syndrome_id, sytech_id)
        res = self._select(sql)
        if len(res) > 0:
            print("【重复数据】", disease_name, syndrome_name, sytech_name)
            return

        # 新增数据
        sql = "insert into sch_sytech " \
              "(status, disease_id, syndrome_id, sytech_id, detail, operation) " \
              "values (%d, %d, %d, %d, '%s', '%s')" \
              % (status, disease_id, syndrome_id, sytech_id, detail, operation)
        self._insert(sql)



    def _select_disease_id(self, disease_name):
        # 查询疾病ID
        sql = "select id from dict_disease " \
              "where content = '%s'" \
              % (disease_name)
        res = self._select(sql)
        if len(res) <= 0:
            return None
        else:
            return res[0][0]


    def _select_syndrome_id(self, disease_id, syndrome_name):
        # 查询辨证ID
        sql = "select id from syn_syndrome " \
              "where disease_id = %d and name = '%s'" \
              % (disease_id, syndrome_name)
        res = self._select(sql)
        if len(res) <= 0:
            return None
        else:
            return res[0][0]


