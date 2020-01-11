# 数据离线同步（data-transfer）

## 介绍

利用datax框架进行数据库数据离线同步

支持全量同步，增量同步，定时任务

## 环境

+ jdk1.8
+ Python 2.7
+ datax

## 配置

+ 下载datax，解压部署，将项目配置文件中的dataxPath配置为datax/bin/datax.py

例如：dataxPath=E:/yonyou/DataTransfer/datax/bin/datax.py

+ pythonPath配置为Python安装目录下的python.exe

例如：pythonPath=C:/Python27/python.exe

+ incrementJsonPath配置为自定义的增量同步的json文件

例如：incrementJsonPath=E:/yonyou/DataTransfer/datax/job/incrementJson.json

+ fileJsonPath配置为自定义的读取最大ID的json文件

例如：fileJsonPath=E:/yonyou/DataTransfer/datax/job/fileJson.json

+ maxTimeFilePth配置为最大ID文件的存储路径

例如：maxTimeFilePath=E:/yonyou/DataTransfer/datax/job/

**注意**

maxTimeFilePath请与fileson.json中配置的path路径一致

+ cron表达式自主配置

## JSON文件

JSON文件请参照datax的JSON文件规范书写

例如：incrementJsonPath.json

```json
{
    "job": {
        "content": [
            {
                "reader": {
                    "parameter": {
                        "password": "cyqakyOcH1", 
                        "column": [
                            "ID", 
                            "FPQQLSH", 
                            "FPLX", 
                            "ZSFS", 
                            "KPLX", 
                            "FPJZ", 
                            "XSF_NSRSBH", 
                            "XSF_MC", 
                            "XSF_DZDH", 
                            "XSF_YHZH", 
                            "GMF_NSRSBH", 
                            "GMF_MC", 
                            "GMF_DZDH", 
                            "GMF_YHZH", 
                            "KPR", 
                            "SKR", 
                            "FHR", 
                            "TSCHBZ", 
                            "YFP_DM", 
                            "YFP_HM", 
                            "JSHJ", 
                            "HJJE", 
                            "HJSE", 
                            "BZ", 
                            "JQBH", 
                            "FP_DM", 
                            "FP_HM", 
                            "KPRQ", 
                            "FP_MW", 
                            "JYM", 
                            "EWM", 
                            "ZDRQ", 
                            "SBBZ", 
                            "LYLX", 
                            "LYID", 
                            "DEF1", 
                            "DEF2", 
                            "DEF3", 
                            "DEF4", 
                            "DEF5", 
                            "tenantid", 
                            "creator", 
                            "ts", 
                            "status", 
                            "bred", 
                            "corpId", 
                            "orgId", 
                            "BMB_BBH", 
                            "SGBZ", 
                            "zdybz", 
                            "createtime", 
                            "zfbz", 
                            "account_status", 
                            "hzxxbbh"
                        ], 
                        "where": "ID>1157103085196845056 and ID<=1157103085196845056", 
                        "connection": [
                            {
                                "jdbcUrl": [
                                    "jdbc:mysql://10.16.2.24:3306/piaoeda_client"
                                ], 
                                "table": [
                                    "einvoice_his"
                                ]
                            }
                        ], 
                        "username": "piaoeda"
                    }, 
                    "name": "mysqlreader"
                }, 
                "writer": {
                    "parameter": {
                        "password": "cyqakyOcH1", 
                        "column": [
                            "ID", 
                            "FPQQLSH", 
                            "FPLX", 
                            "ZSFS", 
                            "KPLX", 
                            "FPJZ", 
                            "XSF_NSRSBH", 
                            "XSF_MC", 
                            "XSF_DZDH", 
                            "XSF_YHZH", 
                            "GMF_NSRSBH", 
                            "GMF_MC", 
                            "GMF_DZDH", 
                            "GMF_YHZH", 
                            "KPR", 
                            "SKR", 
                            "FHR", 
                            "TSCHBZ", 
                            "YFP_DM", 
                            "YFP_HM", 
                            "JSHJ", 
                            "HJJE", 
                            "HJSE", 
                            "BZ", 
                            "JQBH", 
                            "FP_DM", 
                            "FP_HM", 
                            "KPRQ", 
                            "FP_MW", 
                            "JYM", 
                            "EWM", 
                            "ZDRQ", 
                            "SBBZ", 
                            "LYLX", 
                            "LYID", 
                            "DEF1", 
                            "DEF2", 
                            "DEF3", 
                            "DEF4", 
                            "DEF5", 
                            "tenantid", 
                            "creator", 
                            "ts", 
                            "status", 
                            "bred", 
                            "corpId", 
                            "orgId", 
                            "BMB_BBH", 
                            "SGBZ", 
                            "zdybz", 
                            "createtime", 
                            "zfbz", 
                            "account_status", 
                            "hzxxbbh"
                        ], 
                        "connection": [
                            {
                                "jdbcUrl": "jdbc:mysql://10.16.2.24:3306/qw_writer", 
                                "table": [
                                    "einvoice_his"
                                ]
                            }
                        ], 
                        "username": "piaoeda"
                    }, 
                    "name": "mysqlwriter"
                }
            }
        ], 
        "setting": {
            "speed": {
                "channel": 1
            }
        }
    }
}
```

例如：fileJson.json

```json
{
    "job": {
        "content": [
            {
                "reader": {
                    "name": "mysqlreader", 
                    "parameter": {
                        "connection": [
                            {
                                "jdbcUrl": [
                                    "jdbc:mysql://10.16.2.24:3306/piaoeda_client"
                                ], 
                                "querySql": [
                                    "SELECT max(ID) FROM einvoice_his"
                                ]
                            }
                        ], 
                        "password": "cyqakyOcH1", 
                        "username": "piaoeda"
                    }
                }, 
                "writer": {
                    "name": "txtfilewriter", 
                    "parameter": {
                        "fileName": "max_ID", 
                        "path": "E:/yonyou/DataTransfer/datax/job", 
                        "writeMode": "truncate"
                    }
                }
            }
        ], 
        "setting": {
            "speed": {
                "channel": 1
            }
        }
    }
}
```

## 使用

程序启动后，可在控制台看到如下信息

+ 输入数字1，进行一次全量
+ 输入数字2，进行一次增量
+ 输入数字3，进行定时增量
+ 输入数字4，退出

功能如上所示

输入数字3，进行定时增量后，可通过如下url访问对定时任务进行动态调控

+ /start 开始定时任务
+ /info 参数列表name ，group，显示name，group这一定时任务的信息
+ /modiy 参数列表name，group，cron，将name，group的cron表达式修改为cron
+ /insert 参数列表name，group，cron，新增此信息的任务
+ /pause 参数列表name，group，暂停此信息的任务
+ /pauseAll 暂停全部任务
+ /resume 参数列表name，group，恢复此信息的任务
+ /resumeAll 恢复全部任务
+ /delete 参数列表name，group，删除此信息的任务
+ /full 进行一次全量同步

