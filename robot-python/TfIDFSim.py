
from flask import Flask
import math
from math import isnan
from sklearn.feature_extraction.text import TfidfVectorizer
import pymysql



#数据库连接
conn = pymysql.connect(user='root', password='root', database='faq', charset='utf8mb4')
cursor = conn.cursor()
#获取用户
cursor.execute("select  * from  faq")
rows = cursor.fetchall()

app = Flask(__name__)
#结巴分词，切开之后，有分隔符
def jieba_function(sent):
    import jieba
    sent1 = jieba.cut(sent)
    s = []
    for each in sent1:
        s.append(each)
    return ' '.join(str(i) for i in s)
def count_cos_similarity(vec_1, vec_2):
    if len(vec_1) != len(vec_2):
        return 0

    s = sum(vec_1[i] * vec_2[i] for i in range(len(vec_2)))
    den1 = math.sqrt(sum([pow(number, 2) for number in vec_1]))
    den2 = math.sqrt(sum([pow(number, 2) for number in vec_2]))
    return s / (den1 * den2)

def tfidf(sent1, sent2):
    from sklearn.feature_extraction.text import TfidfVectorizer

    sent1 = jieba_function(sent1)
    sent2 = jieba_function(sent2)
    tfidf_vec = TfidfVectorizer()
    sentences = [sent1, sent2]
    vec_1 = tfidf_vec.fit_transform(sentences).toarray()[0]
    vec_2 = tfidf_vec.fit_transform(sentences).toarray()[1]
    similarity=count_cos_similarity(vec_1, vec_2)
    if isnan(similarity):
        similarity=0.0
    return similarity

def returAns(question):
   # question = request.args.get("question")
    maxScore=0.000
    maxFaq=[]
    for row in rows:
        if (row[1]!=None and question!=None):
            score=tfidf(row[1], question)
            if(score>maxScore):
                maxScore=score
                maxFaq=row
    return maxFaq[2]

if __name__ == "__main__":
    returAns("test")