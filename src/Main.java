import java.util.Scanner;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.text.DecimalFormat;


public class Main {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static float Un;
    public static void setUn(float un) {
        Main.Un = un;
    }

    public static float xq;
    public static void setXq(float xq) {
        Main.xq = xq;
    }

    public static float rl;
    public static void setRl(float rl) {
        Main.rl = rl;
    }

    public static float xl;
    public static void setXl(float xl) {
        Main.xl = xl;
    }

    public static float rt;
    public static void setRt(float rt) {
        Main.rt = rt;
    }

    public static float zt;
    public static void setZt(float zt) {
        Main.zt = zt;
    }

    public static float xt;
    public static void setXt(float xt) {
        Main.xt = xt;
    }

    public static float zk;
    public static void setZk(float zk) {
        Main.zk = zk;
    }

    public static int count;
    public static void setCount(int count) {
        Main.count = count;
    }

    public static float[] dlugosc  = new float[100];
    public static float[] gamma  = new float[100];
    public static float[] s  = new float[100];
    public static float[] xprim  = new float[100];

    static void obwod(){
        Scanner in = new Scanner(System.in);
        //obwód
        System.out.println("Podaj Un [kV]");
        float Un = in.nextFloat();
        setUn(Un);
        System.out.println("Podaj S''k [MVA]");
        float sk = in.nextFloat();
        double xq=(1.1*pow(Un,2))/(sk);
        xq=xq*1000;
        setXq((float) xq);
        System.out.println("xq= "+df.format(xq)+"mOM");

    }
    static void transformator(){
        Scanner in = new Scanner(System.in);

        //transformator
        System.out.println("Podaj deltaUk [%]");
        float dUk = in.nextFloat();

        System.out.println("Podaj deltaPn [%]");
        float dPn = in.nextFloat();

        System.out.println("Podaj Snt [kVA]");
        float Snt = in.nextFloat();

        Snt=Snt/1000;

        double rt = (dPn*pow(Un,2))/(100*Snt);
        rt*=1000;
        System.out.println("rt= "+df.format(rt));
        setRt((float) rt);

        double zt = (dUk*pow(Un,2))/(100*Snt);
        zt*=1000;
        System.out.println("zt= "+df.format(zt));
        setZt((float) zt);

        double xt = sqrt(pow(zt,2)-pow(rt,2));
        System.out.println("xt= "+df.format(xt));
        setXt((float) xt);

    }
    static void linieCount(){
        Scanner in = new Scanner(System.in);
        //linie zwykła metoda
        System.out.println("Podaj ilosc linii: ");
        int count = in.nextInt();
        setCount(count);
    }
    static void linieDane(){
        Scanner in = new Scanner(System.in);
        for(int i=0;i<count;i++){
            System.out.println("Podaj długosc[m] linii "+i+" :");
            float dl = in.nextFloat();
            dlugosc[i]=dl;

            System.out.println("Podaj gamme dla linii[33-al,56-cu] "+i+" :");
            float gama = in.nextFloat();
            gamma[i]=gama;

            System.out.println("Podaj s[mm] linii "+i+" :");
            float ss = in.nextFloat();
            s[i]=ss;

            System.out.println("Podaj x' linii "+i+" :");
            float iks = in.nextFloat();
            xprim[i]=iks;
        }
    }
    static void linieZwykla(){
        //linie
        for(int i=0;i<count;i++){
            System.out.println();
            rl+=(dlugosc[i]*1000)/(gamma[i]*s[i]);
            double rlx=(dlugosc[i]*1000)/(gamma[i]*s[i]);
            System.out.println("rl dla linii: " +i+"= "+df.format(rlx)+"mOM");
            xl+=xprim[i]*dlugosc[i];
            double xlx=xprim[i]*dlugosc[i];
            System.out.println("xl dla linii: " +i+"= "+df.format(xlx)+"mOM");
        }
        System.out.println();
        System.out.println("rl(wszystkich)= "+df.format(rl));
        System.out.println("xl(wszystkich)= "+df.format(xl));

        setRl(rl);
        setXl(xl);
    }
    static void linieUproszczona(){
        //linie
        for(int i=0;i<count;i++){
            System.out.println();
            rl+=2*((dlugosc[i]*1000)/(gamma[i]*s[i]));
            double rlx=(dlugosc[i]*1000)/(gamma[i]*s[i]);
            System.out.println("rl dla linii: " +i+"= "+df.format(rlx)+"mOM");
            xl+=2*(xprim[i]*dlugosc[i]);
            double xlx=xprim[i]*dlugosc[i];
            System.out.println("xl dla linii: " +i+"= "+df.format(xlx)+"mOM");
        }

        System.out.println();
        System.out.println("rl(wszystkich)= "+df.format(rl)+"mOM");
        System.out.println("xl(wszystkich)= "+df.format(xl)+"mOM");


        setRl(rl);
        setXl(xl);
    }
    static void obwodZwarciowyZwykla(){
        //obwod zwarciowy
        //rq=0
        System.out.println();
        double rk = 0 + rt + rl;
        System.out.println("rk= " + df.format(rk));
        double xk = xq + xt + xl;
        System.out.println("xk= " + df.format(xk));
        double zk = sqrt(pow(rk, 2) + pow(xk, 2));
        System.out.println("zk= " + df.format(zk));
        setZk((float) zk);
    }
    static void obwodZwarciowyUproszczona(){
        //obwod zwarciowy
        //rq=0
        System.out.println();
        double rk=0+rt+1.24*rl;
        System.out.println("rk= "+df.format(rk)+"mOM");
        double xk=xq+xt+xl;
        System.out.println("xk= "+df.format(xk)+"mOM");
        double zk=sqrt(pow(rk,2)+pow(xk,2));
        System.out.println("zk= "+df.format(zk)+"mOM");
        setZk((float) zk);
    }
    static void zwarcieZwykla(){
        obwod();
        transformator();
        linieCount();
        linieDane();
        linieZwykla();
        obwodZwarciowyZwykla();

        double ik=(1*Un)/(sqrt(3)*zk);
        ik*=1000000;
        System.out.println();
        System.out.println("ik= "+df.format(ik)+"A");
    }
    static void zwarcieUproszczona(){
        obwod();
        transformator();
        linieCount();
        linieDane();
        linieUproszczona();
        obwodZwarciowyUproszczona();

        System.out.println();
        System.out.println("Zwarcie 1 fazowe (230V)");
        double ik=(0.95*230)/zk;
        ik*=1000;
        System.out.println("ik= "+df.format(ik)+"A");

        System.out.println();
        System.out.println("Zwarcie 3 fazowe (400V)");
        double ik3f=(0.95*400)/zk;
        ik3f*=1000;
        System.out.println("ik= "+df.format(ik3f)+"A");
    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.println("1-metoda uproszczona");
        System.out.println("2-metoda zwykła");
        int choice = in.nextInt();
        switch (choice){
            case 1:
                zwarcieUproszczona();
                break;
            case 2:
                zwarcieZwykla();
                break;
            default:
                break;
        }
    }
}