### 作业要求

#### 在demo的基础上完成下面需求：
* 重构添加热搜的接口，热搜事件现在增加一个字段:user，用来表示是哪个用户添加的该热搜事件。
  用户所包含的字段以及字段的限制和demo中保持一致，请求例子：
  ```
  {
      "eventName": "添加一条热搜",
      "keyword": "娱乐",
      "user": {
        "userName": "xiaowang",
        "age": 19,
        "gender": "female",
        "email": "a@thoughtworks.com",
        "phone": 18888888888
      }
  }
  ``` 
  
* 如果userName已存在在user列表中的话则只需添加热搜事件到热搜事件列表，如果userName不存在，则将User添加到热搜事件列表中（相当于注册用户）
* 需要对请求进行校验：其中user keyword eventName都不能为空, user的校验规则：
    ```
  名称(不超过8位字符，不能为空)
  性别（不能为空）
  年龄（18到100岁之间，不能为空）
  邮箱（符合邮箱规范）
  手机号（1开头的11位数字，不能为空）
    ```
  
* 测试需要对每个验证条件进行覆盖

notice: 注意@Valid和@Validated的配合使用



  




<<<<<<< HEAD
=======
#### 实现或修改如下接口
* 修改添加热搜事件接口：参照demo，将添加RsEvent持久化到数据库中
    ```
    {
        “eventName”: “热搜事件名”,
         “keyword”: “关键字”,
         “userId”: “user_id”
    }
  ```
  其中user需要是已注册用户，否则添加失败返回400
  

* 修改删除用户接口：参照demo，删除用户时，需要同时删除该用户所创建的热搜事件(使用JPA提供的mapping注解@ManyToOne @OneToMany)
* 添加更新接口
   ```
    request: patch /rs/{rsEventId}
    requestBody: {
                    “eventName”: “新的热搜事件名”,
                     “keyword”: “新的关键字”,
                     “userId”: “user_id”
                }
   ```
  接口要求：当userId和rsEventId所关联的User匹配时，更新rsEvent信息
          当userId和rsEventId所关联的User不匹配时，返回400
          userId为必传字段
          当只传了eventName没传keyword时只更新eventName
          当只传了keyword没传eventName时只更新keyword
          
* 添加投票接口
    ```
    request: post /rs/vote/{rsEventId}
    request body: {
                    voteNum: 5,
                    userId: 1,
                    voteTime: "current time"
                  }  
    接口要求：如果用户剩的票数大于等于voteNum，则能成功给rsEventId对应的热搜事件投票
            如果用户剩的票数小于voteNum,则投票失败，返回400
            考虑到以后需要查询投票记录的需求（根据userId查询他投过票的所有热搜事件，票数和投票时间，根据rsEventId查询所有给他投过票的用户，票数和投票时间），
            创建一个Vote表是一个明智的选择
            目前不用考虑给热搜事件列表排序的问题
  
    ```
* 修改其余所有接口：所有读取操作都改为从数据库中读取数据（包括重构测试）
  注意：需要修改热搜事件返回的数据结构，使其返回热搜事件id和获得的票数:
    ```
        {
            eventName: "event name",
            keyword: "keyword",
            id: "id",
            voteNum: 10
        }
    ```

* 写测试！！！
>>>>>>> a517b2d4007fc9662e1935248e0ebec735b2f6e8


