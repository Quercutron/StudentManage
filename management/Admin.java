package com.itheima.management;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Admin {
    public static void main(String[] args) {
        ArrayList<User> list=new ArrayList<>();
        while (true) {
            System.out.println("欢迎来到学生管理系统");
            Scanner sc=new Scanner(System.in);
            System.out.println("请选择操作1登录 2注册 3忘记密码");
            String choose=sc.next();
            switch (choose){
                case "1": login(list);break;
                case "2": enroll(list);break;
                case "3": forgetPassword(list);break;
                case "4":{
                    System.out.println("谢谢使用，再见。");
                    System.exit(0);
                }
                default:
                    System.out.println("无此选项");
            }

            for (int i = 0; i < list.size(); i++) {
                User user = list.get(i);
                System.out.println("用户名为："+user.getName());
                System.out.println("用户密码为："+user.getPassword());
                System.out.println("用户身份证号为："+user.getId());
                System.out.println("用户密码为："+user.getPhoneNumber());
            }
        }
    }

    //登录
    public static void login(ArrayList<User> list){
        System.out.println("登录");
        Scanner sc=new Scanner(System.in);
        if(list.size()==0){
            System.out.println("用户名未注册，请先注册");
            return;
        }//登录
        loop:while (true) {
            User adminUser=new User();
            System.out.println("请输入用户名：");
            String name=sc.next();
            System.out.println("请输入密码：");
            String password=sc.next();

            adminUser.setName(name);
            adminUser.setPassword(password);

            //验证码
            String captcha = null;
            String captchUser= null;
            while (true) {
                captcha = captcha();
                System.out.println("验证码："+captcha);
                captchUser = sc.next();
                if (captchUser.equalsIgnoreCase(captcha)){
                    System.out.println("验证码正确！");
                    break;
                }else {
                    System.out.println("验证码错误");
                    continue;
                }
            }

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getName().equals(adminUser.getName() )&& list.get(i).getPassword().equals(adminUser.getPassword())&& captchUser.equals(captcha)){
                    System.out.println("登录成功！");
                    StudentSystem ss=new StudentSystem();
                    ss.startSystem();
                    break ;
                }else {
                    System.out.println("用户名或密码错误,您还有"+(3-i-1)+"次机会。");
                    System.exit(3);
                }
            }
        }
    }

    //注册
    public static void enroll(ArrayList<User> list){
        System.out.println("注册");
        User user=new User();
        Scanner sc=new Scanner(System.in);
        //验证用户名
        while (true) {
            System.out.println("请输入用户名：");
            String name = sc.next();
            int index = checkName(list, name);
            if (index>0){
                System.out.println("用户名已存在，请重新输入。");
            }else if (name.length()<3||name.length()>15){
                System.out.println("用户名长度必须在3~15位之间 ");
            }else if (!checkNumber(name)){
                System.out.println("只能是字母加数字的组合，但是不能是纯数字");
            } else {
                user.setName(name);
                System.out.println("用户名输入正确。");
                break;
            }
        }

        //键盘录入两次密码
        while (true) {
            System.out.println("请输入第一次密码：");
            String password1=sc.next();
            System.out.println("请再次输入密码：");
            String password2=sc.next();
            if (password1.equals(password2)){
                user.setPassword(password1);
                System.out.println("密码输入正确。");
                break;
            }else {
                System.out.println("两次密码不一致。");
            }
        }

        //身份证号码
        while (true) {
            System.out.println("请输入身份证号码：");
            String id=sc.next();
            if (id.length()!=18){
                System.out.println("请输入18位身份证号码。");
            }else if (id.charAt(0)=='0'){
                System.out.println("不能以0为开头");
            }else if (checkId(id,id.length()-1)){
                System.out.println("前17位，必须都是数字");
            }else if (endId(id)){
                System.out.println("最为一位可以是数字，也可以是大写X或小写x");
            }else {
                user.setId(id);
                System.out.println("身份证号码输入成功！");
                break;
            }
        }

        //手机号验证
        while (true) {
            System.out.println("请输入手机号码：");
            String phoneNumber=sc.next();
            if (phoneNumber.length()!=11){
                System.out.println("长度为11位");
            }else if (phoneNumber.charAt(0)=='0'){
                System.out.println("不能以0为开头");
            }else if (checkId(phoneNumber,phoneNumber.length())){
                System.out.println("必须都是数字");
            }else {
                user.setPhoneNumber(phoneNumber);
                System.out.println("身份证输入成功.");
                break;
            }
        }

        list.add(user);
        System.out.println("注册成功！");
    }

    //忘记密码
    public static void forgetPassword(ArrayList<User> list){
        System.out.println("忘记密码");
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入用户名：");
        String name=sc.next();
        if (checkName(list,name)<1){
            System.out.println("用户名不存在");
            return;
        }

        while (true) {
            String id=sc.next();
            System.out.println("请输入身份证号码：");
            String phoneNumber= sc.next();
            System.out.println("请输入手机号码：");
            for (int i = 0; i < list.size(); i++) {
                if (phoneNumber.equals(list.get(i).getPhoneNumber())&&id.equals(list.get(i).getId())){
                    System.out.println("验证成功，请输入密码：");
                    String newPassword=sc.next();
                    list.get(i).setPassword(newPassword);
                    System.out.println("密码修改成功");
                    break;
                }else {
                    System.out.println("账号信息不匹配，修改失败。");
                }
            }
        }
    }

    //检查用户名是否存在
    public static int checkName(ArrayList<User> list,String name){
        if (list.size()==0){
            return -1;
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)){
                return i;
            }
        }
        return -1;
    }

    //检查字符串是否都为数字。
    public static boolean checkNumber(String name){
        for (int i = 0; i < name.length(); i++) {
            //获得每一位字符
            char c = name.charAt(i);
            if (c<'0'||c>'9'){
                return true;
            }
        }
        return false;
    }

    //验证字符串的前num位是否都为数字
    public static boolean checkId(String id,int num){//18,17
        for (int i = 0; i <num; i++) {
            if (id.charAt(i)<'0'||id.charAt(i)>'9'){
                return true;
            }
        }
        return false;
    }

    //检查id的最后一位是否符合
    public static boolean endId(String id){
        char endId = id.charAt(id.length() - 1);
        if ((endId!='x'||endId!='X')&&(endId<'0'||endId>'9')){
            return true;
        }
        return false;
    }

    //生成验证码
    public static String captcha(){
        //在数组中添加52个大小写字母
        char[] arr=new char[52];
        for (int i = 0; i < arr.length; i++) {
            if (i<26) {
                arr[i]=(char) (97+i);
            }else {
                arr[i]=(char) (65+i-26);
            }
        }
        //在数组中随机选取四个字母
        Random r=new Random();
        String str="";
        for (int i = 0; i < 4; i++) {
            int index = r.nextInt(arr.length);
            str=str+arr[index];
        }
        //最后一位为数字
        int number = r.nextInt(10);
        str+=number;
        //打乱数字位置
        char[] arrString=str.toCharArray();
        //随机位置坐标。
        int ranNumber = r.nextInt(arrString.length);
        char c;
        c=arrString[arrString.length-1];
        arrString[arrString.length-1]=arrString[ranNumber];
        arrString[ranNumber]=c;
        //将字符数组变回字符串
        String strs=new String(arrString);

        return strs;
    }

    //遍历集合与输入是否一致。
    public static String checkLogin(ArrayList<User> list){
        String str="";
        for (int i = 0; i < list.size(); i++) {
            str=list.get(i).getName();
        }
        return str;
    }
}
