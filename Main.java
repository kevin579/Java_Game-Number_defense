package secondGame;
import hsa2.GraphicsConsole;
import java.awt.Color;
import java.util.*;
import java.awt.Font;
public class Main2 {
    public static void main(String []args) {
        new Main2();
    }
    final static int WINW = 1000, WINH = 600;
    final static int WIDE =20;
    GraphicsConsole gc = new GraphicsConsole(WINW,WINH+100);
    int [][] graph = new int[(WINH)/WIDE][(WINW-100)/WIDE];  
    int [][] atk = new int[WINH/WIDE][(WINW-100)/WIDE];  
    Color bgColor = new Color(249,214,91);
    //249,214,91
    int cashfreq = 300;
    int times = 1;
    int enemyfreq = 1000;
    int moveSpeed = 25;
    int cashlimit = 0;
    int cash = 50000;
    int hp = 5;
    int ne = 0;
    int lives = 10;
    int score =0;
    int level =1;
    int tt = 0;
    int numberoffarm = 2;
    int limit3 = 10;
    int choice = 0;
    int totalenemy = 100;
    int time = 0;
    int tower2 = 10;
    int tower3 = 15;
    int tower4 = 5;
    int tower5 = 10;
    int tower6 = 30;
    int [][] allenemy = new int[totalenemy][3];
    Font small = new Font("Impact", Font.PLAIN,16);
    Font big = new Font("Impact", Font.PLAIN,20);
    Main2(){
        setup();
        rungame();
    }
    
    void setup(){
        gc.setTitle("Laser defence");
        gc.setAntiAlias(true);
        gc.setResizable(false);
        gc.setLocationRelativeTo(null);
        gc.setBackgroundColor(bgColor);
        gc.clear();

        gc.setFont(big);
        gc.enableMouseMotion();
        gc.enableMouse();
        gc.enableMouseWheel();
        gc.setColor(Color.BLACK);
        updateAllGraphics();
    }

    void rungame(){
        int active =0;
        int type =0;
        while (lives>0){   
            int x = 0;
            int y = 0;
            if (gc.getMouseClick() >0){
                x = gc.getMouseX();
                y = gc.getMouseY();
            }

            int gx = (x-100)/WIDE;
            int gy = y/WIDE;
            if (y>WINH) continue;
            if (x<=100){
                active = 0;
            }
            else{
                active = 1;
            }
            if (active == 0){
                for (int i =0;i<=WINH-cashlimit;i+=100){
                    if (0<x && x<100 && y>i-100 && y<i){
                        synchronized(gc){
                            choice = i;
                            type = choice/100+1;
                            updateAllGraphics();
                        }
                        gc.setColor(Color.BLACK);
                        break;
                    }
                }
                if (active == 1)continue;
            }
            if (active == 1 && type!=0 && x>=100 && y!=0){  
                while (true){
                    if (y>=WINH)break;
                    if (graph[gy][gx]==0){
                        if (drawDot(type,x,y) == true){
                            graph[gy][gx] = type;
                            updateAllGraphics();
                            active = 0;
                            for (int[] i:atk){
                                System.out.println(Arrays.toString(i));
                            }
                           break; //bb
                            
                        }
                    }
                    y+=WIDE;
                    gy+=1;
                }
            }
            time +=1;
            timeEvents();
        } // while loop close hear
    gc.showDialog("Socre: " + score, "Score"); 
    }
    void timeEvents(){
        if (time%6000==0){
            level+=1;
            updateInfo();
            enemyfreq-=10;
            moveSpeed -=1;
            time-=5999;
            hp+=2*times;
            times+=1;
        }
        gc.sleep(5);
        if (time%enemyfreq ==0 && ne <totalenemy) {
            int[] enemy = createEnemy();
            allenemy[ne] = enemy;
            ne+=1;
        }
        if (time%moveSpeed ==0){
            moveEnemy(allenemy);
            updateAllGraphics();        

        }
        if (time%cashfreq ==0){
            cash+=numberoffarm;
            gc.setColor(bgColor);
            updateAllGraphics();

        }
        if (time%100 ==0) {
            score+=1;
            //updateScore();
            updateAllGraphics();
        }    
    }
    void updateTab(){
        gc.setStroke(1);
        gc.drawLine(100,0,100,WINH);
        for (int i =100;i<=WINH;i+=100){
            gc.drawLine(0,i,100,i);
        }
        gc.drawString("H-Lazer: $" + tower2,2,55);
        gc.drawString("V-Lazer: $" + tower3,2,155);
        gc.drawString("Area: $" + tower4,2,255);
        gc.drawString("Short: $" + tower5,2,355);
        gc.drawString("Strong: $" + tower6,2,455);
        gc.drawString("Farm: $" + numberoffarm*10,4,555);
    }
    void updateGrid(){
        
        gc.setColor(Color.BLACK);
        gc.setStroke(1);
        for (int i=100;i<=Math.max(WINH,WINW);i+=WIDE){
            gc.drawLine(i,0,i,WINH);
            if (i-100<=WINH)gc.drawLine(100,i-100,100+((WINW-100)/WIDE)*WIDE,i-100);
        }
    }
    void updateInfo(){
        //gc.setColor(bgColor);
        //gc.fillRect(104,605,90,90);
        gc.setColor(Color.BLACK);
        gc.drawString("Cash: " + cash, 110, 650);
        gc.drawString("Lives: " + lives, 310, 650);
        gc.drawString("Level: " + level, 510, 650);
        gc.drawString("Score: " + score, 710, 650);
    }
    void updateChoice(){
        gc.setStroke(4);
        gc.drawRect(2,choice-98,96,96);
    }
    void updateBackground(){
        gc.clear();
        gc.setColor(Color.GRAY);
        gc.fillRect(0,0,WINW,WINH+100);
        gc.setColor(bgColor);
        gc.fillRect(100,0,WINW-100,WINH);
        gc.setColor(Color.PINK);
        gc.fillRect(0,WINH,WINW,100);
    }

    void updateEnemy(){
        for (int i =0;i<ne;i++){
            if (allenemy[i][2] >0) {
                gc.setColor(Color.BLACK);
                gc.setFont(small);
                gc.drawString(""+ allenemy[i][2],allenemy[i][0]*WIDE+102,allenemy[i][1]*WIDE+14);
            }
        }
        gc.setFont(big);
    }

    void updateTower(){
        for (int y = 0; y<WINH/WIDE;y++){
            for (int x = 0; x<(WINW-100)/WIDE;x++){
                if (atk[y][x]!=0){
                        double dtransparent = (Math.log(atk[y][x]*30)-1)*40-50;
                        int transparent = (int) dtransparent;
                            gc.setColor(new Color(255,0,0,Math.min(transparent,240)));
                            gc.fillRect(x*WIDE+100,y*WIDE,WIDE,WIDE);
                }
                switch (graph[y][x]){
                    case 2:
                        gc.setColor(Color.BLACK);
                        gc.fillOval(x*WIDE+102,y*WIDE+6,WIDE-12,WIDE-12); 
                        gc.fillRect(x*WIDE+102,y*WIDE+8,WIDE-4,WIDE-16); 
                        break;
                    case 3:
                        gc.setColor(Color.WHITE);
                        gc.fillOval(x*WIDE+105,y*WIDE+5,WIDE-10,WIDE-10); 
                        gc.fillRect(x*WIDE+108,y*WIDE+2,WIDE-16,WIDE-4);  
                        break;
                    case 4:
                        gc.setColor(Color.YELLOW);
                        gc.fillOval(x*WIDE+102,y*WIDE+2,WIDE-4,WIDE-4);
                        break;
                    case 5:
                        gc.setColor(Color.BLUE);
                        gc.fillRect(x*WIDE+110,y*WIDE+2,WIDE-12,WIDE-4); 
                        gc.fillRect(x*WIDE+102,y*WIDE+6,WIDE-4,WIDE-12); 

                        break;
                    case 6:
                        gc.setColor(Color.GRAY);
                        gc.fillStar(x*WIDE+102, y*WIDE+2, WIDE-4, WIDE-4);
                        break;
                    case 7:
                        gc.setColor(Color.GREEN);
                        gc.fillOval(x*WIDE+102,y*WIDE+2,WIDE-4,WIDE-4); 
                        break;
                }
            }
        }
    }
    void updateAllGraphics(){
        synchronized(gc){
            updateBackground();
            updateInfo();
            updateTab();
            updateChoice();
            updateEnemy();
            updateTower();
            updateGrid();

        }   
    }

    boolean drawDot(int type, int x, int y){
        int gx = (x-100)/WIDE;
        int gy = y/WIDE;
        gc.setStroke(3);
        switch(type){
            case 2:
                if (cash >=tower2){
                    cash-=tower2;
                    gc.setColor(new Color(255,0,0,30));
                    //tower2 +=1;
                    for (int xx = gx ; xx<(WINW-100)/WIDE;xx++){
                        atk[gy][xx]+=1;
                    }
                    return true;
                }
                else return false;
            case 3:
                if (cash >=tower3 &&tt<limit3){
                    cash-=tower3;
                    tt+=1;
                    //tower3 +=1;
                    gc.setColor(new Color(255,0,0,30));

                    for (int yy = 0;yy<WINH/WIDE;yy++){
                        atk[yy][gx] +=2;
                    }
                    return true;
                }
                else return false;
            case 4:
                if (cash >=tower4){
                    cash-=tower4;
                    //tower4 +=1;
                    for (int i =Math.max(gy-2,0);i<=Math.min(gy+2,WINH/WIDE-1);i++){
                        for (int j = gx;j<=Math.min(gx+7,(WINW-100)/WIDE-1);j+=1){
                            atk[i][j] +=2;
                            if (i ==0 || i ==WINH/WIDE-1) atk[i][j] +=2;
                        }
                        
                    }
                    
                    return true;
                }
                else return false;
            case 5:
                if (cash>=tower5){
                    cash-=tower5;
                    //tower5 +=1;
                    if (gx<(WINW-100)/WIDE-1){
                        atk[gy][gx+1] +=15;
                    }
                    return true;
                }
                else return false;
            case 6:
            if (cash>=tower6){
                    cash-=tower6;
                    //tower6 +=1;
                    for (int i =Math.max(gy-2,0);i<=Math.min(gy+2,WINH/WIDE-1);i++){
                        for (int j = gx;j<=Math.min(gx+3,(WINW-100)/WIDE-1);j+=1){
                            atk[i][j] *=2;
                        }
                    }
                    return true;
            }
            else return false;
            case 7:
                if(cashfreq>40 && numberoffarm<10){
                    if (cash>=10*numberoffarm){
                        cash-=10*numberoffarm;
                        cashfreq-=15;
                        numberoffarm+=1;
                        return true;
                    }
                }
                else {
                    cashlimit = 20;
                    return false;
                }
        }
        return false;
    }
    int [] createEnemy(){
        int x  = (WINW-100)/WIDE;
        int y = 2;
        hp = (int) (hp*1.1)+1;
        int [] enemy = {x,y,hp};
        return enemy;
    }
    void moveEnemy(int[][] allenemy){
         for (int i =0;i<ne;i++){
            if (allenemy[i][2] <=0) {
                allenemy[i][0] = (WINW-100)/WIDE-1;
                allenemy[i][1] = (int) (Math.random()*WINH/WIDE);
                allenemy[i][2] = hp;
                cash+=level*2;
                score +=hp;
                updateAllGraphics();
                continue;
            }
            allenemy[i][0]-=1;
            int updown = (int)(Math.random()*5)-2;
            if (allenemy[i][1] == 0 || allenemy[i][1] == 1) updown = 2;
            else if (allenemy[i][1] == WINH/WIDE-1 ||allenemy[i][1] == WINH/WIDE-2) updown = -2;
            allenemy[i][1]+=updown;
            allenemy[i][2] -=atk[allenemy[i][1]][allenemy[i][0]];
            switch (graph[allenemy[i][1]][allenemy[i][0]]){
                case 0:
                    break;
                case 2:
                    graph[allenemy[i][1]][allenemy[i][0]] = 0;
                    for (int xx = allenemy[i][0] ; xx<(WINW-100)/WIDE;xx++){
                        atk[allenemy[i][1]][xx]-=1;
                    }
                    break;
                case 3:
                    graph[allenemy[i][1]][allenemy[i][0]] = 0;
                    for (int yy = 0;yy<WINH/WIDE;yy++){
                        atk[yy][allenemy[i][0]] -=2;
                    }
                    break;
    
                case 4:
                    graph[allenemy[i][1]][allenemy[i][0]] = 0;
                    for (int u =Math.max(allenemy[i][1]-2,0);u<=Math.min(allenemy[i][1]+2,WINH/WIDE-1);u++){
                        for (int j = allenemy[i][0];j<=Math.min(allenemy[i][0]+7,(WINW-100)/WIDE-1);j+=1){
                            atk[u][j] -=2;
                        }
                    }
                    break;
                case 5:
                    graph[allenemy[i][1]][allenemy[i][0]] = 0;
                    if (allenemy[i][0]<(WINW-100)/WIDE-1){
                        atk[allenemy[i][1]][allenemy[i][0]+1] -=15;
                    }
                    break;
                case 6:
                    graph[allenemy[i][1]][allenemy[i][0]] = 0;
                    for (int u =Math.max(allenemy[i][1]-2,0);u<=Math.min(allenemy[i][1]+2,WINH/WIDE-1);u++){
                        for (int j = allenemy[i][0];j<=Math.min(allenemy[i][0]+3,(WINW-100)/WIDE-1);j+=1){
                            atk[u][j] /=2;
                        }
                    }
                    break;
                case 7:
                    cashfreq +=100;
                    break;

            }
            if (allenemy[i][0] ==0) {
                gc.setColor(bgColor);
                gc.fillRect(allenemy[i][0]*WIDE+102,allenemy[i][1]*WIDE+2,WIDE-4,WIDE-4);
                gc.setColor(Color.BLACK);
                allenemy[i][0] = (WINW-100)/WIDE-1;
                lives -=1;
                updateAllGraphics();
            }
         }
    }
}
