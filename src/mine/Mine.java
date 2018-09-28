package mine;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Mine extends  JFrame implements ActionListener, MouseListener {

    int no_hay_minas = 80;
    int permitir[][];
    
    String tmp;
    boolean encontrar = false;
    
    int fila;
    int columnas;
    int matriz[][];
    
    JButton b[][];
    
    int[][] minas;
    
    boolean todaslasminas;
    
    int n = 10;
    int m = 10;
    int eje_x[] = {-1, 0, 1, -1, 1, -1, 0, 1};
    int eje_y[] = {-1, -1, -1, 0, 0, 1, 1, 1};
    
    double iniciartiempo;
    double finalizartiempo;

    public Mine() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        permitir = new int[n][m];
        boolean todaslasminas = false;
        matriz = new int [ n + 2 ][ m + 2 ];
        minas = new int[ n + 2 ][ m + 2 ];
        b = new JButton [n][m];
        
        //gridlayout alinea los elementos en columnas y filas
        setLayout(new GridLayout( n,m ));
        
        for (int y = 0; y < m + 2; y++){
            minas[0][y] = 3;
            minas[ n + 1 ][y] = 3;
            matriz[0][y] = 3;
            matriz[ n + 1 ][y] = 3;
        }
        for (int x = 0; x < n + 2; x++){
            minas[x][0] = 3;
            minas[x][ m + 1] = 3;
            matriz[x][0] = 3;
            matriz[x][ m + 1] = 3;
        }
        do {
            int verificar = 0;
            for (int y = 1; y < m + 1; y++){
                for (int x = 1; x < n + 1; x++){
                    minas[x][y] = 0;
                    matriz[x][y] = 0;
                }
            }
            for (int x = 0; x < no_hay_minas; x++){
                minas [(int) (Math.random()*( n )+ 1)][(int) (Math.random()*(m) + 1)] = 1;
            }
            for (int x = 0;x < n ; x++){
                for (int y = 0; y < m; y++){
                if (minas[ x + 1][ y + 1] == 1){
                        verificar++;
                    }
                }
            }
            if (verificar == no_hay_minas){
                todaslasminas = true;
            }
        }while (todaslasminas == false);
        for (int y = 0;y<m;y++){
            for (int x = 0;x<n;x++){
                if ((minas[x+1][y+1] == 0) || (minas[x+1][y+1] == 1)){
                    permitir[x][y] = perimcheck(x,y);
                }
                b[x][y] = new JButton("?");
                b[x][y].addActionListener(this);
                b[x][y].addMouseListener(this);
                add(b[x][y]);
                b[x][y].setEnabled(true);
            }
        }
        pack();
        setVisible(true);
        for (int y = 0; y < m + 2 ; y++){
            for (int x = 0; x < n + 2; x++){
                System.out.print(minas[x][y]);
            }
        System.out.println("");}
        iniciartiempo = System.nanoTime();
    }

    
public void actionPerformed(ActionEvent e){
        encontrar =  false;
        JButton current = (JButton)e.getSource();
        for (int y = 0; y< m ; y++){
            for (int x = 0; x < n ; x++){
                JButton t = b[x][y];
                if(t == current){
                    fila=x;columnas=y; encontrar =true;
                }
            }
        }
        if(!encontrar) {
            System.out.println("didn't find the button, there was an error "); System.exit(-1);
        }
        Component temporaryLostComponent = null;
        if (b[fila][columnas].getBackground() == Color.orange){
            return;
        }else if (minas[fila + 1][columnas + 1] == 1){
                JOptionPane.showMessageDialog(temporaryLostComponent, "You set off a Mine!!!!.");
                System.exit(0);
        } else {
            tmp = Integer.toString(permitir[fila][columnas]);
            if (permitir[fila][columnas] == 0){
                    tmp = " ";
            }
            b[fila][columnas].setText(tmp);
            b[fila][columnas].setEnabled(false);
            checkifend();
            if (permitir[fila][columnas] == 0){
                scan(fila, columnas);
                checkifend();
            }
        }
    }
 
    public void checkifend(){
        int verificar = 0;
        for (int y = 0; y < m ; y++){
            for (int x = 0; x < n ; x++){
        if (b[x][y].isEnabled()){
            verificar ++;
        }
            }}
        if (verificar == no_hay_minas){
            finalizartiempo = System.nanoTime();
            Component temporaryLostComponent = null;
            JOptionPane.showMessageDialog(temporaryLostComponent, "felicidades ganastes!!! te tomo... "+(int)((finalizartiempo-iniciartiempo)/1000000000)+" seconds!");
        }
    }
 
    public void scan(int x, int y){
        for (int a = 0; a < 8 ; a++){
            if (minas[ x + 1 + eje_x[a]][ y + 1 + eje_y[a]] == 3){
 
            } else if ((permitir[ x + eje_x[a]][ y + eje_y[a]] == 0) && (minas[ x + 1 + eje_x[a]][ y + 1 + eje_y[a]] == 0) && (matriz[x+eje_x[a]+1][y+eje_y[a]+1] == 0)){
                if (b[ x + eje_x[a]][ y + eje_y[a]].isEnabled()){
                    b[ x + eje_x[a]][ y + eje_y[a]].setText(" ");
                    b[ x + eje_x[a]][ y + eje_y[a]].setEnabled(false);
                    scan(x+eje_x[a], y + eje_y[a]);
                }
            } else if ((permitir[x+eje_x[a]][y+eje_y[a]] != 0) && (minas[x+1+eje_x[a]][y+1+eje_y[a]] == 0)  && (matriz[x+eje_x[a]+1][y+eje_y[a]+1] == 0)){
                tmp = new Integer(permitir[x+eje_x[a]][y+eje_y[a]]).toString();
                b[x+eje_x[a]][y+eje_y[a]].setText(Integer.toString(permitir[x+eje_x[a]][y+eje_y[a]]));
                b[x+eje_x[a]][y+eje_y[a]].setEnabled(false);
            }
        }
    }
 
    public int perimcheck(int a, int y){
        int minecount = 0;
        for (int x = 0;x<8;x++){
            if (minas[a+eje_x[x]+1][y+eje_y[x]+1] == 1){
                minecount++;
            }
        }
        return minecount;
    }
 
    public void windowIconified(WindowEvent e){
 
    }
 
    public static void main(String[] args){
        new Mine();
    }
 
    public void mouseClicked(MouseEvent e) {
 
    }
 
    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }
 
    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }
 
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            encontrar =  false;
            Object current = e.getSource();
            for (int y = 0; y < m ; y++){
                    for (int x = 0; x < n ; x++){
                            JButton t = b[x][y];
                            if(t == current){
                                    fila = x ; columnas = y ; encontrar =true;
                            }
                    }
            }
            if(encontrar) {
                System.out.println("didn't find the button, there was an error "); System.exit(-1);
            }
            if ((matriz[fila+1][columnas+1] == 0) && (b[fila][columnas].isEnabled())){
                b[fila][columnas].setText("x");
                matriz[fila+1][columnas+1] = 1;
                b[fila][columnas].setBackground(Color.orange);
            } else if (matriz[fila+1][columnas+1] == 1){
                b[fila][columnas].setText("?");
                matriz[fila+1][columnas+1] = 0;
                b[fila][columnas].setBackground(null);
            }
        }
    }
 
    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }
}                     

    
    

                




    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
        
        
     
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
