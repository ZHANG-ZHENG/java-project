1）生成服务端私钥，并且导入到服务端KeyStore文件中
keytool -genkey -alias serverkey -keystore kserver.keystore
过程中，分别需要填写，根据需求自己设置就行
keystore密码：123456
名字和姓氏：jin
组织单位名称：none
组织名称：none
城市或区域名称：cd
州或省份名称：sc
国家代码：cn
serverkey私钥的密码，不填写和keystore的密码一致。这里千万注意，直接回车就行了，不用修改密码。否则在后面的程序中以及无法直接应用这个私钥，会报错。

生成kserver.keystore文件是给服务端用的，其中保存着自己的私钥
2）根据私钥，导出服务端证书
keytool -export -alias serverkey -keystore kserver.keystore -file server.crt
server.crt就是服务端的证书

3）将服务端证书，导入到客户端的Trust KeyStore中
keytool -import -alias serverkey -file server.crt -keystore tserver.keystore
tserver.keystore是给客户端用的，其中保存着受信任的证书

采用同样的方法，生成客户端的私钥，客户端的证书，并且导入到服务端的Trust KeyStore中
1）keytool -genkey -alias clientkey -keystore kclient.keystore
2）keytool -export -alias clientkey -keystore kclient.keystore -file client.crt
3）keytool -import -alias clientkey -file client.crt -keystore tclient.keystore

如此一来，生成的文件分成两组
服务端保存：
kserver.keystore 发送给客户端，客户端拿到该密匙后与tserver.keystore进行验证，确认是否是自己的目标服务器
tclient.keystore 用于验证客户端发来的秘钥
客户端保存：
kclient.keystore 发送给服务器，服务器拿到该密匙后与tclient.keystore比较验证，确实该客户端是否有权访问
tserver.kyestore 用于验证服务器发来的秘钥


-------------------------------------------------------------------------------------------------------
keytool -genkey -alias keystore -keyalg RSA -keystore keystore.jks

