package com.example.bank;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * ATM系统启动类
 * 需求：
 * 实现一个银行ATM系统
 * 包含：用户登陆、注册、设置账户单次限额、查询、存款、取款、转账、其他、退卡
 */
public class AccountTest {
    public static void main(String[] args) {
        //1、定义账户类
        //2、定义一个集合容器，负责以后存储全部的账户对象，进行相关业务操作。
        ArrayList<AccountInfo> account = new ArrayList<>();
        while (true) {
            System.out.println("=======黑马ATM系统=======");
            System.out.println("1、账户登陆");
            System.out.println("2、注册账户");
            Scanner sc = new Scanner(System.in);
            System.out.print("请您选择操作：");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    //用户登陆操作
                    login(account, sc);
                    break;
                case 2:
                    register(account, sc);
                    //用户账户开户
                    break;
                default:
                    System.out.println("您输入的操作命令不存在");
                    break;
            }
        }
    }


    //实现用户登陆功能
    public static void login(ArrayList<AccountInfo> accout, Scanner sc) {
        System.out.println("=====黑马银行ATM系统登陆界面=====");
        //判断账户集合中是否有账户
        if (accout.size() == 0) {
            System.out.println("对不起，当前系统中没有任何账户，请先开户，再来登陆");
            return;
        }
        while (true) {
            System.out.print("请您输入您的卡号：");
            String strId = sc.next();
            boolean flag = false;
            for (int i = 0; i < accout.size(); i++) {
                String cardId = accout.get(i).getAccountId();
                if (strId.equals(cardId)) {
                    flag = true;
                    while (true) {
                        String pwd = accout.get(i).getPwd();
                        System.out.print("请您输入您的密码：");
                        String strPwd = sc.next();
                        if (!strPwd.equals(pwd)) {
                            System.out.println("密码有误重新输入");
                        } else {
                            System.out.println(accout.get(i).getName() + "贵宾，欢迎您进入系统，您的卡号为："
                                    + accout.get(i).getAccountId());
                            //进入查询、转账等操作
                            //展示登陆后的操作页
                            showUserComand(accout, accout.get(i), sc);
                            return;//结束登陆方法
                        }
                    }
                }
            }
            if (!flag) {
                System.out.println("不存在该卡号");
            }
        }

    }

    /**
     * 展示登陆后的操作页
     */
    private static void showUserComand(ArrayList<AccountInfo> accounts, AccountInfo accountInfo, Scanner sc) {
        while (true) {
            System.out.println("=====欢迎您进入黑马银行用户操作界面=====");
            System.out.println("1、查询：");
            System.out.println("2、存款：");
            System.out.println("3、取款：");
            System.out.println("4、转账：");
            System.out.println("5、修改密码：");
            System.out.println("6、退出：");
            System.out.println("7、注销当前账户：");
            System.out.print("您可继续选择功能进行操作：");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    //查询
                    showAccount(accountInfo);
                    break;
                case 2:
                    depositMoney(accountInfo, sc);
                    //存款
                    break;
                case 3:
                    //取款
                    withdrawMoney(accountInfo, sc);
                    break;
                case 4:
                    //转账
                    transferMoney(accounts, accountInfo, sc);
                    break;
                case 5:
                    //修改密码
                    changePwd(accountInfo, sc);
                    return;
                case 6:
                    //退出
                    System.out.println("退出成功，欢迎下次登陆");
                    //退出之后，结束当前方法
                    return;
                case 7:
                    //注销账户
                    removeCount(accounts, accountInfo, sc);
                    return;
                default:
                    System.out.println("您输入的命令不对，请重新输入。");
            }
        }
    }

    /**
     * 注销账户
     *
     * @param accounts
     * @param accountInfo
     * @param sc
     */
    private static void removeCount(ArrayList<AccountInfo> accounts, AccountInfo accountInfo, Scanner sc) {
        System.out.println("======欢迎您进入账户注销界面=====");

        while (true) {
            System.out.print("输入卡号进行注销确认：");
            String accountId = sc.next();
            if (accountId.equals(accountInfo.getAccountId())) {
                for (int i = 0; i < accounts.size(); i++) {
                    accounts.remove(accountInfo);
                }
                System.out.println("注销账户成功");
                return;
            } else {
                System.out.println("账户输入错误，重新输入。");
            }
        }
    }

    /**
     * 修改账户密码
     *
     * @param accountInfo
     * @param sc
     */
    private static void changePwd(AccountInfo accountInfo, Scanner sc) {
        System.out.println("=====欢迎您进入修改账户密码界面=====");
        while (true) {
            System.out.print("请您输入当前账户的密码：");
            String pwd = sc.next();
            if (!pwd.equals(accountInfo.getPwd())) {
                System.out.println("密码输入错误，请重新输入。");
            } else {
                while (true) {
                    System.out.print("请输入新的密码： ");
                    String newPwd1 = sc.next();
                    System.out.print("请您确认密码：");
                    String newPwdConfirm = sc.next();
                    if (newPwd1.equals(newPwdConfirm)) {
                        accountInfo.setPwd(newPwd1);
                        System.out.println("修改密码成功，请重新登录");
                        return;
                    } else {
                        System.out.println("两次输入的密码不一致，重新输入。");
                    }
                }
            }
        }

    }

    /**
     * 转账功能实现
     *
     * @param accountInfo
     * @param sc
     */

    private static void transferMoney(ArrayList<AccountInfo> accounts, AccountInfo accountInfo, Scanner sc) {
        System.out.println("====欢迎您进入黑马银行用户转账界面====");
        if (accounts.size() < 2) {
            System.out.println("当前系统，账户不足两个，不能转账");
            return;
        } else if (accountInfo.getBalance() <= 0) {
            System.out.println("您当前账户余额为0，不可转账");
            return;
        } else {
            while (true) {
                System.out.print("请您输入转账的账户卡号：");
                String cardId = sc.next();
                boolean flag = false;
                if (cardId.equals(accountInfo.getAccountId())) {
                    System.out.println("对不起，您不可以给自己转账~~");
                    continue;//结束当此执行，进入下一次
                }
                for (int i = 0; i < accounts.size(); i++) {
                    if (accounts.get(i).getAccountId().equals(cardId)) {
                        flag = true;
                        String name = accounts.get(i).getName();
                        String laterName = name.substring(1);
                        System.out.println("您当前要为" + "*" + laterName + "转账");
                        System.out.print("请您输入姓氏确认：");
                        String familyName = sc.next();
                        String allName = familyName + laterName;
                        if (!allName.equals(name)) {
                            System.out.println("当前用户不是你要转账的用户，请确认账户卡号");
                        } else {
                            while (true) {
                                System.out.print("输入您要转账的金额：");
                                double money = sc.nextDouble();
                                if (money > accountInfo.getBalance()) {
                                    System.out.println("当前账户余额不足，账户余额为：" + accountInfo.getBalance());
                                } else if (money > accountInfo.getLimit()) {
                                    System.out.println("对不起，您当前的取款金额超过每次限额。\n" +
                                            "每次最多可取" + accountInfo.getLimit() + "元");
                                } else {
                                    accountInfo.setBalance(accountInfo.getBalance() - money);
                                    accounts.get(i).setBalance(accounts.get(i).getBalance() + money);
                                    System.out.println("转账成功，账户信息如下");
                                    showAccount(accountInfo);
                                    return;
                                }
                            }
                        }
                        return;
                    }
                }
                if (!flag) {
                    System.out.println("不存在该卡号，请重新输入。");
                }
            }
        }
    }

    /**
     * 取款功能实现
     *
     * @param accountInfo
     * @param sc
     */
    private static void withdrawMoney(AccountInfo accountInfo, Scanner sc) {
        System.out.println("=======用户取款操作======");
        //1、先判断自己账户是否有钱
        if (accountInfo.getBalance() < 100) {
            System.out.println("对不起，当前账户中金额不够100，不能取款。");
            return;
        }
        while (true) {
            System.out.print("请您输入取款金额：");
            double money = sc.nextDouble();
            if (money > accountInfo.getLimit()) {
                System.out.println("对不起，您当前的取款金额超过每次限额。\n" +
                        "每次最多可取" + accountInfo.getLimit() + "元");
            } else if (money > accountInfo.getBalance()) {
                System.out.println("当前账户余额不足，请重新输入");
                System.out.println("您当前账户余额为：" + accountInfo.getBalance());
            } else {
                accountInfo.setBalance(accountInfo.getBalance() - money);
                System.out.println("恭喜你，取款成功，当前账户信息如下：");
                showAccount(accountInfo);
                return;
            }
        }

    }

    /**
     * 存款功能实现
     *
     * @param accountInfo 当前账户对象
     */
    private static void depositMoney(AccountInfo accountInfo, Scanner sc) {
        System.out.println("=======用户存款操作=======");
        System.out.print("请您输入存款金额：");
        double money = sc.nextDouble();
        accountInfo.setBalance(accountInfo.getBalance() + money);
        System.out.println("恭喜你，存款成功，当前账户信息如下：");
        showAccount(accountInfo);
    }

    /**
     * @param accountInfo 传入当前账户相关信息
     */
    private static void showAccount(AccountInfo accountInfo) {
        System.out.println("======当前账户信息如下======");
        System.out.println("卡号：" + accountInfo.getAccountId());
        System.out.println("户主：" + accountInfo.getName());
        System.out.println("余额：" + accountInfo.getBalance());
        System.out.println("限额：" + accountInfo.getLimit());
    }

    //实现用户注册功能

    /**
     * @param account
     * @param sc
     */
    public static void register(ArrayList<AccountInfo> account, Scanner sc) {
        AccountInfo accountInfo = new AccountInfo();
        String pwd;
        String pwdConfirm;
        System.out.println("========欢迎您进入开户操作=========");
        System.out.print("请您输入账号名称：");
        String name = sc.next();
        accountInfo.setName(name);
        while (true) {
            System.out.print("请您输入账号密码：");
            pwd = sc.next();
            if (pwd.length() != 6) {
                System.out.println("账户密码为6位，请重新输入：");
                pwd = sc.next();
            }
            System.out.print("请您确认密码：");
            pwdConfirm = sc.next();
            if (!pwd.equals(pwdConfirm)) {
                System.out.println("两次输入的密码不一致！");
            } else {
                break;
            }
        }
        accountInfo.setPwd(pwd);
        System.out.print("请您设置单次取现额度：");
        double limit = sc.nextDouble();
        accountInfo.setLimit(limit);
        //生成卡号
        String accountId = accoutId(account);
        System.out.println("恭喜您，" + name + "先生/女士，您开户完成，您的卡号是：" + accountId);
        accountInfo.setAccountId(accountId);
        account.add(accountInfo);
    }

    //随机生成8位卡号并保证卡号不重复
    public static String accoutId(ArrayList<AccountInfo> account) {
        Random ran = new Random();
        String str = "";
        //判断卡号是否唯一
        while (true) {
            for (int i = 0; i < 8; i++) {
                int number = ran.nextInt(10) + 48;
                char ch = (char) number;
                str += ch;
            }
            //根据卡号判断查询账户是否存在
            AccountInfo acc = repeat(account, str);
            if (acc == null) {
                return str;
            }
        }
    }

    /**
     * @param account
     * @param str     随机生成的账户卡号
     * @return
     */
    //判断卡号是否重复
    public static AccountInfo repeat(ArrayList<AccountInfo> account, String str) {
        for (int i = 0; i < account.size(); i++) {
            AccountInfo acc = account.get(i);
            if (acc.getAccountId().equals(str)) {
                return acc;
            }
        }
        return null;
    }
}
