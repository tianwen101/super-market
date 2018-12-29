package com.soft1841.oop.Op;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

// 业务类
public class Business {
    //集合类 存放会员
    ArrayList<Member> a=new ArrayList<Member>();
    Member member=new Member();
    Scanner input =new Scanner(System.in);
    //主页面方法
    public void f1(){
        System.out.println("***********************************欢迎光临超市购物系统***********************************");
        System.out.println("1.开通会员账户\t2.积分查询\t3.积分积累\t4.积分兑换\t5.修改密码\t6.退出系统");
        System.out.println("*************************************************************************************");
        xuanze();
    }
    public void xuanze(){
        do{
            System.out.print("请选择：");
            int x=input.nextInt();
            switch(x){
                case 1://1.开通账户
                    kaitong();
                    continue;
                case 2:  //积分查询
                    chaxun();
                    continue;
                case 3://积分积累
                    jilei();
                    continue;
                case 4://积分兑换
                    duihuan();
                    continue;
                case 5://修改密码
                    gaimi();
                    continue;
                case 6://退出系统
                    System.out.println("退出系统，欢迎您的下次光临！");
                    break;
                default:
                    System.out.println("您的选择有误，请重新选择：");
                    continue;
            }
            break;
        }while(true);
    }
    //1.开通账户
    public void kaitong(){
        System.out.println("********************开通会员账户 ********************");
        System.out.print("请输入会员名称：");
        String name=input.next();
        member.setName(name);
        do{
            System.out.print("请输入会员密码：");
            String pwd=input.next();

            //密码长度不能小于6位
            if(pwd.length()<6){
                System.out.println("密码长度不能小于6位，请重新输入!");
            }else{
                //产生8位随机卡号
                Random random=new Random();
                int careId=random.nextInt(999999999);
                member.setIntegral(100);
                member.setCareId(careId);
                member.setPassword(pwd);
                a.add(member);
                //开卡成功 赠送100积分
                System.out.println("恭喜您会员开卡成功，赠送您100积分，您的会员卡号是："+careId);
                System.out.println("**************************************************");
                break;
            }
        }while(true);

    }
    //2.积分查询
    public void chaxun(){
        System.out.println("********************积分查询 ********************");
        //System.out.println("您的密码是："+member.getPassword());
        System.out.print("请输入您的会员卡号：");
        int id=input.nextInt();
        System.out.print("请输入您的会员密码：");
        String pwd=input.next();
        for(int i=0;i<a.size();i++){//判断密码和会员账户是非正确
            if(a.get(i).getCareId()==id && a.get(i).getPassword().equals(pwd)){
                //显示会员信息
                System.out.println("会员名"+"\t"+"会员卡号"+"\t"+"\t"
                        +"会员密码"+"\t"+"会员积分"+"\t"+"开卡日期");
                //开卡日期
                Date date=new Date();
                SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
                System.out.println(member.getName()+"\t"+member.getCareId()+"\t"+
                        member.getPassword()+"\t"+member.getIntegral()+"\t"+s.format(date));
            }else{
                System.out.println("会员卡号或密码错误，不能查询！");
            }
        }
        System.out.println("**************************************************");
    }
    //3.积分积累
    public void jilei(){
        System.out.println("********************积分积累 ********************");
        System.out.print("请输入您的会员卡号：");
        int id=input.nextInt();
        System.out.print("请输入您的会员密码：");
        String pwd=input.next();
        for(int i=0;i<a.size();i++){//判断密码和会员账户是非正确
            if(a.get(i).getCareId()==id && a.get(i).getPassword().equals(pwd)){
                System.out.println("请输入您此次消费金额：");
                double menoy=input.nextDouble();
                member.setIntegral(menoy+member.getIntegral());
                System.out.println("您现在的积分是："+(member.getIntegral()));
            }else{
                System.out.println("会员卡号或密码错误，不能查询！");
            }
        }
        System.out.println("**************************************************");
    }
    //t4.积分兑换
    public void duihuan(){
        System.out.println("********************积分兑换 ********************");
        System.out.print("请输入您的会员卡号：");
        int id=input.nextInt();
        System.out.print("请输入您的会员密码：");
        String pwd=input.next();
        for(int i=0;i<a.size();i++){//判断密码和会员账户是非正确
            if(a.get(i).getCareId()==id && a.get(i).getPassword().equals(pwd)){
                System.out.println("请输入您此次兑换积分数：");
                double menoy=input.nextDouble();
                double m=menoy/100*0.1;
                System.out.println("您此次积分兑换金额是："+m);
                member.setIntegral(member.getIntegral()-menoy);
                System.out.println("恭喜您兑换成功！您的剩余积分是："+(member.getIntegral()));
            }else{
                System.out.println("会员卡号或密码错误，不能查询！");
            }

        }
        System.out.println("**************************************************");
    }
    //t5.修改密码
    public void gaimi(){
        System.out.println("********************修改密码 ********************");
        System.out.print("请输入您的会员卡号：");
        int id=input.nextInt();
        System.out.print("请输入您的会员名称：");
        String name=input.next();
        System.out.println("请输入您的会员密码：");
        String pwd=input.next();
        for(int i=0;i<a.size();i++){
            if(a.get(i).getCareId()==id && a.get(i).getPassword().equals(pwd) && a.get(i).getName().equals(name)){
                System.out.println("请输入您的新密码：");
                String pwd1=input.next();
                if(pwd.length()<6){
                    System.out.println("密码长度不能小于6位，请重新输入!");
                }else{
                    member.setPassword(pwd1);
                    System.out.println("修改密码成功，您的新密码是："+pwd1);
                }
            }else{
                System.out.print("会员卡号或密码错误，不能查询！");
            }
        }
        System.out.println("**************************************************");
    }

}
