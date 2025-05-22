package com.itheima.management;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentSystem {
    //一拖四结构。
    public static void main(String[] args) {
        ArrayList<student> list=new ArrayList<>();
        loop:while (true) {
            System.out.println("----------欢迎来到黑马学生管理系统------------");
            System.out.println("1:添加学生");
            System.out.println("2:删除学生");
            System.out.println("3:修改学生");
            System.out.println("4:查询学生");
            System.out.println("5:退出");
            System.out.println("请输入您的选择：");
            Scanner sc=new Scanner(System.in);
            String choose = sc.next();
            switch (choose){
                case "1": addStudent(list);break;
                case "2":deleteStudent(list);break;
                case "3":updateStudent(list);break;
                case "4":queryStudent(list);break;
                case "5":{
                    System.out.println("退出");
                    break loop;
                }
                default:
                    System.out.println("无此选项！");
            }
        }
    }

    //添加
    public static void addStudent(ArrayList<student> list){
        System.out.println("添加学生");
        student str=new student();
        Scanner sc=new Scanner(System.in);
        String id;
        while (true) {
            System.out.println("请输入学生的学号：");
            id= sc.next();
            if (!check(list,id)) {
                break;
            }else {
                System.out.println("学号存在!");
            }
        }

        System.out.println("请输入学生的姓名：");
        String name = sc.next();
        System.out.println("请输入学生的年龄：");
        int age=sc.nextInt();
        System.out.println("请输入学生的家庭住址：");
        String address = sc.next();

        //添加到对象中
        str.setId(id);
        str.setName(name);
        str.setAge(age);
        str.setAddress(address);

        //添加到集合中
        list.add(str);

        System.out.println("添加成功！");
    }

    //删除
    public static void deleteStudent(ArrayList<student> list){
        System.out.println("删除学生");
        if (list.size()==0){
            System.out.println("当前无学生信息，请添加后删除。");
            return;
        }
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入您要删除的学生学号：");
        String id=sc.next();
        if (!check(list,id)){
            System.out.println("id不存在");
        }else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId().equals(id)){
                    list.remove(i);
                    System.out.println("删除成功！");
                }
            }
        }
    }

    //修改
    public static void updateStudent(ArrayList<student> list){
        System.out.println("修改学生");
        Scanner sc=new Scanner(System.in);
        if (list.size()==0){
            System.out.println("当前无学生信息，请添加后修改。");
            return;
        }
        System.out.println("请输入您要修改的学生信息的学号：");
        String id=sc.next();
        if (!check(list,id)){
            System.out.println("id不存在");
            return;
        }
        System.out.println("id存在，请添加修改信息！");
        int index=0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(id)){
                index=i;
            }
        }
        student str=list.get(index);

        System.out.println("请输入学生的姓名：");
        String name = sc.next();
        System.out.println("请输入学生的年龄：");
        int age=sc.nextInt();
        System.out.println("请输入学生的家庭住址：");
        String address = sc.next();

        //添加到对象中
        str.setName(name);
        str.setAge(age);
        str.setAddress(address);

        System.out.println("修改成功！");

    }

    //查询
    public static void queryStudent(ArrayList<student> list){
        System.out.println("查询学生");
        //检查id
        if (list.size()==0){
            System.out.println("当前无学生信息，请添加后查阅。");
        }else {
            //遍历学生信息。
            System.out.println("id\t\t姓名\t\t年龄\t家庭住址");
            for (int i = 0; i < list.size(); i++) {
                student student = list.get(i);
                System.out.println(student.getId()+"\t\t"+student.getName()+"\t\t"+student.getAge()+"\t\t"+student.getAddress());
            }
        }
    }

    //检查id是否存在.
    public static boolean check(ArrayList<student> list,String id){
        boolean flag=false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(id)){
                flag=true;
            }
        }
        return flag;
    }

}
