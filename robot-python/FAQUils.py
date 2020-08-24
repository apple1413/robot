#python 3.8
#WebQA Json解析程序

import json
import pymysql
conn = pymysql.connect(
    host='localhost',  # mysql服务器地址
    port=3306,  # 端口号
    user='root',  # 用户名
    passwd='root',  # 密码
    db='faq',  # 数据库名称
    charset='utf8',  # 连接编码，根据需要填写
)
cur = conn.cursor()  # 创建并返回游标
questionList = open(r"E:\gongzhonghao\robot\WebQA.v1.0(1)\WebQA.v1.0\me_train.json", "r",encoding='UTF-8')
questionList = json.load(questionList)
num=1
for lineQuestion in questionList:
    questionDict=questionList[lineQuestion]
    question = questionDict['question']
    evidences = questionDict['evidences']
    for ed in evidences:
        if evidences[ed]['answer'][0]!='no_answer':
            answer=evidences[ed]['answer'][0]+"  "+evidences[ed]['evidence']
            insertSql = " insert into faq (fid,question,answer)VALUES(%s,%s,%s)"
            #insertSql = " insert into faq (fid,question,answer)VALUES('1','2','3')"
            cur.execute(insertSql,(str(num),question,answer))
            conn.commit()
            print(str(num))
            num+=1
            break


