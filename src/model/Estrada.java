
package model;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;

/**
 *
 * @author mayron
 */
public class Estrada extends JButton {
    private int instrucao;
    private boolean ocupado;

    public Estrada() {
        this.ocupado = false;
        this.instrucao = 0;
        this.setText("");
        this.setBorder(null);
        this.setBorderPainted(false);
        this.setPreferredSize(new java.awt.Dimension(20, 20));
    }
    
    
    public int getInstrucao() {
        return instrucao;
    }

    public void setInstrucao(int instrucao) {
        this.instrucao = instrucao;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setCollor() {
        if (instrucao == 0) {
            this.setBackground(Color.WHITE);
        }
    }
    
    public void setOcupado(boolean ocupado, String c) {
        if (ocupado == true) {
            this.ocupado = true;
            if (this.instrucao == 2 || instrucao == 4) {
                this.setText("▄▄");
            }else if(this.instrucao == 1 || instrucao == 3){
                this.setText("█");
            }else{
                this.setText(c);
            }
            
        }else{
            this.ocupado = false;
            this.setText("");
        }
    }
    
    
    public static Estrada[][] montaMalha(String opcao) throws FileNotFoundException, IOException{
        FileReader arq = null;
        
            String linha = null;
            int linhas = 0;
            int colunas = 0;
            arq = new FileReader("C:\\Users\\mayro\\Documents\\NetBeansProjects\\SimTrafego\\src\\Malhas\\malha"+opcao+".txt");
            BufferedReader br = new BufferedReader(arq);
            linhas = Integer.parseInt(br.readLine());
            colunas = Integer.parseInt(br.readLine());
            Estrada[][] estradas = new Estrada[linhas][colunas];
            
            for (int i = 0; i < estradas.length; i++) {
            
            for (int j = 0; j < estradas[0].length; j++) {
                estradas[i][j] = new Estrada();
            }
        }
            
            
            for (int i = 0; i < linhas; i++) {
                linha = br.readLine();
                String[] li = linha.split(" ");
                for (int j = 0; j < colunas; j++) {
                    estradas[i][j].setInstrucao(Integer.parseInt(li[j]));
                }
            }
            
            return estradas;
    }
    
}
