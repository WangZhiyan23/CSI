# CSI

## 项目介绍
一个员工管理系统（CSI员工之家）

## 注意事项
所有 git 指令如无特别说明，均在 cmd 中项目目录中执行

## 获取项目
```
// 在目标文件夹执行
git clone git@github.com:twoyoung04/CSI.git

// 切换分支，统一在dev分支下进行开发吧
git checkout dev
```

## 项目开发注意事项
```
// 每次开发前先拉取代码，即在项目下执行
git pull // 若报错在群里提出

// 每完成一部分开发，执行
git add .  // 添加所有修改代码到暂存区
git commit -m "blablabla"  // 引号中为本次修改内容的简要注释，如: "增加登录功能"
git push  // 推送到远程仓库

// 首次 push 的过程如出现问题，则执行
git push --set-upstream origin dev
```

## 反馈
git 使用过程中有任何不明白的问题及时在群里提出