package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class   Main {
    static ArrayList<Account> accounts = new ArrayList<>();
    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<CreditCard> cards = new ArrayList<>();

    public static void main(String[] args) {
        Scanner inputdata = new Scanner(System.in);

        createDefaultUsersAccountsAndCards();

        while (true) {
            System.out.println("lütfen müşteri numarınızı giriniz veya uygulamadan çıkış için 0 a basınız");
            String customnum = inputdata.next();
            if (customnum.equals("0")) {
                System.exit(-1);
            }
            System.out.println("parolanızı giriniz");
            String password = inputdata.next();
            //müşteri numarası ve şifreye göre kullanıcıyı alıp geçici bir kulalnıcı nesnesine atadım
            User user = findUserByCustomerNumber(customnum, password);
            if (user != null) {

                System.out.println("sign in succesful");
                System.out.println("User name and surname: " + user.getName() + " " + user.getSurname());
                System.out.println("");
                //burdan sonra kullanıcı isterse tekrar ' eft yapmak için 1'e kredi kartı borcunuzu ödemek için 2 ye basınız ' kısmına dönebilsin diye while içine aldım
                while (true) {
                    System.out.println("eft yapmak için 1'e\r\nkredi kartı borcunuzu ödemek için 2'ye\r\nYeni Hesap Açılışı için 3'e\r\nYeni Kredi Kartı tanımlamak için 4'e basınız");
                    System.out.println("kullanıcı çıkışı için 0 a basınız");

                    int selectedInput = inputdata.nextInt();
                    if (selectedInput == 0) {
                        break;
                    } else if (selectedInput == 4) {
                        addCardToUser(user,1000,0);
                        user.showCredicard();
                    } else if (selectedInput == 3) {
                        addAccountToUser(user,0);
                        user.showBankAccount();
                    } else if (selectedInput == 2) {
                        //kullanıcının sahip olduğu kredi kartları gösteriliyor    projenin bundan sonrası eksik  burdan devam edicem
                        user.showCredicard();
                        System.out.println("choose the creditCard");
                        int credicartindex=inputdata.nextInt();
                        CreditCard creditCard;
                        try
                        {
                            creditCard=user.getCreditCard(credicartindex);
                            System.out.println("borç ödeyeceğiniz hesabı Iban numarasını giriniz ");
                            user.showBankAccount();
                            String Iban=inputdata.next();
                            Account borçödeyecekhesap= findAccountByIBAN(Iban);
                            if(borçödeyecekhesap==null)
                            {
                                System.out.println(" False Iban ");
                                continue;
                            }
                            System.out.println("current dept: "+creditCard.getTotaldebt()+"\n minimumödenecekborç: "+creditCard.getMinimumdebt());
                            System.out.println("ödenecek miktarı giriniz");
                            int amountofdebt=inputdata.nextInt();
                            if(amountofdebt>=creditCard.getMinimumdebt() && amountofdebt<=creditCard.getTotaldebt())
                            {
                                creditCard.payDebt(borçödeyecekhesap,amountofdebt);
                                System.out.println("transaction succesful \n your accounts");
                                user.showBankAccount();
                                System.out.println("kalan borcunuz: "+creditCard.getTotaldebt()+"\n");
                            }else
                            {
                                System.out.println("geçerli miktar giriniz");
                                continue;
                            }

                        }catch (Exception e)
                        {
                            System.out.println("hatalı seçim yaptınız be");
                            continue;
                        }
                    } else if (selectedInput == 1) { // eft yapmak isteyen kullanıcı geldi
                        System.out.println(" hesaplara gitmek için başlarındaki sayılara basınız");
                        // hesapları  gösteriliyor
                        user.showBankAccount();
                        int gelenhesap = inputdata.nextInt();
                        Account selectedAccount;
                        try {
                            //getaccount metodu User sınıfın içinde arraylistte verilenm index e göre hesabı
                            selectedAccount = user.getAccount(gelenhesap - 1);
                        } catch (Exception e) {
                            // Array indexleri dışında bir seçim yapılırsa burada yakalanıp müşteri uyarılır
                            System.out.println("Hatalı seçim yaptınız");
                            continue;
                        }

                        System.out.println("eft yapacağınız hesabın ibannumarasını giriniz...");
                        String IBAN = inputdata.next();

                        // burda ibanı da kontrol etmem lazım fakat dah orayı yapmadım...
                        //findAccountByIBAN metoduyla iban numarası girilen hesabı almıştım onu geçici bir hesap nesnesine atadım
                        Account toAccount = findAccountByIBAN(IBAN);

                        if (toAccount == null) {
                            System.out.println("Hatalı IBAN");
                            continue;
                        }

                        System.out.println("para miktarını giriniz");
                        int money = inputdata.nextInt();
                        if (selectedAccount.eftTransaction(toAccount, money)) {
                            System.out.println(" işlem başarılı ");
                            user.showBankAccount();
                        } else {
                            continue;
                        }

                    } else {
                        System.out.println("geçerli bir seçim yapınız");
                    }
                }

            } else {
                System.out.println("kullanıcı bulunamadı");
                continue;
            }
        }

    }

    public static void createDefaultUsersAccountsAndCards() {
        User user1 = new User("Hasan", "cilhen", "12345678", "cilhen@gmail.com", "123456", "05354344345");
        User user2 = new User("Can", "çevder", "87654321", "çevder@gmail.com", "1234567", "05214054559");
        users.add(user1);
        users.add(user2);

        addAccountToUser(user1, 950);
        addCardToUser(user1, 2000, 1000);

        addAccountToUser(user2, 2000);
        addCardToUser(user2, 4000, 1200);

    }

    public static void addAccountToUser(User user, int initialBalance) {
        Account account = new Account(initialBalance, accounts.size() + 1, "TR200000120002000000110102" + accounts.size());
        user.addBankaccount(account);
        accounts.add(account);
    }

    public static void addCardToUser(User user, int cardLimit, int dept) {
        CreditCard card = new CreditCard(cardLimit, "2000300040005000100" + cards.size(), dept);
        user.addCreditcar(card);
        cards.add(card);
    }

    // girilen müşterinumarası ve şifreye göre kullanıcı var mı onu kontrol ediliyor uygunnvarsa giriş yapabiliyor
    public static User findUserByCustomerNumber(String customerNumber, String password) {
        User tempUser = null;
        for (int i = 0; i < users.size(); i++) {
            if (customerNumber.equals(users.get(i).getCustomerNumber()) && password.matches(users.get(i).getPassword())) {
                tempUser = users.get(i);
            }
        }

        return tempUser;
    }
    //eft için diğer hesabı alıyorum girilen  ibannumarasına göre
    public static Account findAccountByIBAN(String comingIban) {
        Account incominguser = null;
        for (int i = 0; i < accounts.size(); i++) {
            if (comingIban.matches(accounts.get(i).getIbannumber())) {
                incominguser = accounts.get(i);
            }
        }
        return incominguser;
    }


}
