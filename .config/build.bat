#快速编译打包apk脚本

echo "$$package-begin$$"
sleep 1
#执行打包命令前，需要先定位到项目根目录

cd..
gradlew build
#执行打包命令
#方式1.执行项目打包所必须的任务集
#gradle assemble

#方式2.依次打测试包和正式包
gradle assembleThetest
gradle assembleRelease

echo -e "$$package success$$"

#桌面右上角弹出通知
nofity-send build.sh "package down!"