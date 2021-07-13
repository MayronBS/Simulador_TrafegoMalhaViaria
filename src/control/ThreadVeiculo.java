/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MalhaViaria;

/**
 *
 * @author mayron
 */
public class ThreadVeiculo extends Thread {

    private int lAtual;
    private int cAtual;
    private int velocidade;
    private boolean executa;

    public ThreadVeiculo(int lAtual, int cAtual, int velocidade) {
        this.lAtual = lAtual;
        this.cAtual = cAtual;
        this.velocidade = velocidade;
        this.executa = true;
        this.start();

    }

    @Override
    public void run() {
        while (executa == true) {
            if (fim() == false) {
                if (isCruzamento() == false) {
                    if (proxOcupado() == false) {
                        try {
                            move();} catch (InterruptedException ex) {Logger.getLogger(ThreadVeiculo.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                } else {
                    if (cruzamentoVazio() == true) {
                        int tipocruzamento = tipoCruzamento();
                        Random r = new Random();
                        int sortdirecao;
                        if (tipocruzamento == 1) {
                            sortdirecao = r.nextInt(3) + 1;
                            try {
                                executaCruzamento(sortdirecao, tipocruzamento);} catch (InterruptedException ex) {Logger.getLogger(ThreadVeiculo.class.getName()).log(Level.SEVERE, null, ex);}
                        } else {
                            sortdirecao = r.nextInt(2) + 1;
                            try {
                                executaCruzamento(sortdirecao, tipocruzamento);} catch (InterruptedException ex) {Logger.getLogger(ThreadVeiculo.class.getName()).log(Level.SEVERE, null, ex);}
                        }
                    } else {
                        try {
                            this.sleep(300);} catch (InterruptedException ex) {  Logger.getLogger(ThreadVeiculo.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                }
                try {
                    this.sleep(velocidade);} catch (InterruptedException ex) {Logger.getLogger(ThreadVeiculo.class.getName()).log(Level.SEVERE, null, ex);}
            } else {
                executa = false;
            }
        }
    }

    public boolean fim() {
        int instrucao = MalhaViaria.getInstance().getEstradas()[lAtual][cAtual].getInstrucao();

        if ((instrucao == 4 && cAtual == 0) || (instrucao == 1 && lAtual == 0)
                || (instrucao == 2 && cAtual == MalhaViaria.getInstance().getEstradas()[0].length - 1)
                || (instrucao == 3 && lAtual == MalhaViaria.getInstance().getEstradas().length - 1)) {
            MalhaViaria.getInstance().getEstradas()[lAtual][cAtual].setOcupado(false, "");
            return true;
        } else {
            return false;
        }

    }

    public boolean isCruzamento() {
        boolean b = false;
        switch (MalhaViaria.getInstance().getEstradas()[lAtual][cAtual].getInstrucao()) {
            case 1:
                if (MalhaViaria.getInstance().getEstradas()[lAtual - 1][cAtual].getInstrucao() > 4) {
                    b = true;
                }
                break;
            case 2:
                if (MalhaViaria.getInstance().getEstradas()[lAtual][cAtual + 1].getInstrucao() > 4) {
                    b = true;
                }
                break;
            case 3:
                if (MalhaViaria.getInstance().getEstradas()[lAtual + 1][cAtual].getInstrucao() > 4) {
                    b = true;
                }
                break;
            case 4:
                if (MalhaViaria.getInstance().getEstradas()[lAtual][cAtual - 1].getInstrucao() > 4) {
                    b = true;
                }
                break;
        }
        return b;
    }

    public boolean proxOcupado() {
        boolean b = false;
        switch (MalhaViaria.getInstance().getEstradas()[lAtual][cAtual].getInstrucao()) {
            case 1:
                b = MalhaViaria.getInstance().getEstradas()[lAtual - 1][cAtual].isOcupado();
                break;
            case 2:
                b = MalhaViaria.getInstance().getEstradas()[lAtual][cAtual + 1].isOcupado();
                break;
            case 3:
                b = MalhaViaria.getInstance().getEstradas()[lAtual + 1][cAtual].isOcupado();
                break;
            case 4:
                b = MalhaViaria.getInstance().getEstradas()[lAtual][cAtual - 1].isOcupado();
                break;
        }
        return b;
    }

    public void move() throws InterruptedException {
        switch (MalhaViaria.getInstance().getEstradas()[lAtual][cAtual].getInstrucao()) {
            case 1:
                moverCima();
                break;
            case 2:
                moverDireita();
                break;
            case 3:
                moverBaixo();
                break;
            case 4:
                moverEsquerda();
                break;
        }
    }

    public void moverDireita() throws InterruptedException {
        MalhaViaria.getInstance().getEstradas()[lAtual][cAtual + 1].setOcupado(true, "▄▄");
        MalhaViaria.getInstance().getEstradas()[lAtual][cAtual].setOcupado(false, "");
        cAtual++;
        this.sleep(this.velocidade);
    }

    public void moverEsquerda() throws InterruptedException {
        MalhaViaria.getInstance().getEstradas()[lAtual][cAtual - 1].setOcupado(true, "▄▄");
        MalhaViaria.getInstance().getEstradas()[lAtual][cAtual].setOcupado(false, "");
        cAtual--;
        this.sleep(this.velocidade);
    }

    public void moverBaixo() throws InterruptedException {
        MalhaViaria.getInstance().getEstradas()[lAtual + 1][cAtual].setOcupado(true, "█");
        MalhaViaria.getInstance().getEstradas()[lAtual][cAtual].setOcupado(false, "");
        lAtual++;
        this.sleep(this.velocidade);
    }

    public void moverCima() throws InterruptedException {
        MalhaViaria.getInstance().getEstradas()[lAtual - 1][cAtual].setOcupado(true, "█");
        MalhaViaria.getInstance().getEstradas()[lAtual][cAtual].setOcupado(false, "");
        lAtual--;
        this.sleep(this.velocidade);
    }

    public boolean cruzamentoVazio() {
        boolean a = false;
        boolean b = false;
        boolean c = false;
        boolean d = false;
        boolean x = true;
        switch (MalhaViaria.getInstance().getEstradas()[lAtual][cAtual].getInstrucao()) {
            case 1:
                a = MalhaViaria.getInstance().getEstradas()[lAtual - 1][cAtual].isOcupado();
                b = MalhaViaria.getInstance().getEstradas()[lAtual - 2][cAtual].isOcupado();
                c = MalhaViaria.getInstance().getEstradas()[lAtual - 2][cAtual - 1].isOcupado();
                d = MalhaViaria.getInstance().getEstradas()[lAtual - 1][cAtual - 1].isOcupado();
                break;
            case 2:
                a = MalhaViaria.getInstance().getEstradas()[lAtual][cAtual + 1].isOcupado();
                b = MalhaViaria.getInstance().getEstradas()[lAtual][cAtual + 2].isOcupado();
                c = MalhaViaria.getInstance().getEstradas()[lAtual - 1][cAtual + 2].isOcupado();
                d = MalhaViaria.getInstance().getEstradas()[lAtual - 1][cAtual + 1].isOcupado();
                break;
            case 3:
                a = MalhaViaria.getInstance().getEstradas()[lAtual + 1][cAtual].isOcupado();
                b = MalhaViaria.getInstance().getEstradas()[lAtual + 2][cAtual].isOcupado();
                c = MalhaViaria.getInstance().getEstradas()[lAtual + 2][cAtual + 1].isOcupado();
                d = MalhaViaria.getInstance().getEstradas()[lAtual + 1][cAtual + 1].isOcupado();
                break;
            case 4:
                a = MalhaViaria.getInstance().getEstradas()[lAtual][cAtual - 1].isOcupado();
                b = MalhaViaria.getInstance().getEstradas()[lAtual][cAtual - 2].isOcupado();
                c = MalhaViaria.getInstance().getEstradas()[lAtual + 1][cAtual - 2].isOcupado();
                d = MalhaViaria.getInstance().getEstradas()[lAtual + 1][cAtual - 1].isOcupado();
                break;
        }
        if (a == true || b == true || c == true || d == true) {
            x = false;
        }
        return x;
    }

    public int tipoCruzamento() {
        int tipo = 0;
        int soma = 0;
        switch (MalhaViaria.getInstance().getEstradas()[lAtual][cAtual].getInstrucao()) {
            case 1:
                soma += MalhaViaria.getInstance().getEstradas()[lAtual - 1][cAtual].getInstrucao();
                soma += MalhaViaria.getInstance().getEstradas()[lAtual - 2][cAtual].getInstrucao();
                soma += MalhaViaria.getInstance().getEstradas()[lAtual - 2][cAtual - 1].getInstrucao();
                soma += MalhaViaria.getInstance().getEstradas()[lAtual - 1][cAtual - 1].getInstrucao();
                break;
            case 2:
                soma += MalhaViaria.getInstance().getEstradas()[lAtual][cAtual + 1].getInstrucao();
                soma += MalhaViaria.getInstance().getEstradas()[lAtual][cAtual + 2].getInstrucao();
                soma += MalhaViaria.getInstance().getEstradas()[lAtual - 1][cAtual + 2].getInstrucao();
                soma += MalhaViaria.getInstance().getEstradas()[lAtual - 1][cAtual + 1].getInstrucao();
                break;
            case 3:
                soma += MalhaViaria.getInstance().getEstradas()[lAtual + 1][cAtual].getInstrucao();
                soma += MalhaViaria.getInstance().getEstradas()[lAtual + 2][cAtual].getInstrucao();
                soma += MalhaViaria.getInstance().getEstradas()[lAtual + 2][cAtual + 1].getInstrucao();
                soma += MalhaViaria.getInstance().getEstradas()[lAtual + 1][cAtual + 1].getInstrucao();
                break;
            case 4:
                soma += MalhaViaria.getInstance().getEstradas()[lAtual][cAtual - 1].getInstrucao();
                soma += MalhaViaria.getInstance().getEstradas()[lAtual][cAtual - 2].getInstrucao();
                soma += MalhaViaria.getInstance().getEstradas()[lAtual + 1][cAtual - 2].getInstrucao();
                soma += MalhaViaria.getInstance().getEstradas()[lAtual + 1][cAtual - 1].getInstrucao();
                break;
        }

        switch (soma) {
            case 42:
                tipo = 1;
                break;
            case 40:
                tipo = 2;
                break;
            case 38:
                tipo = 3;
                break;
            case 37:
                tipo = 4;
                break;
        }
        return tipo;
    }

    private synchronized void executaCruzamento(int sortdirecao, int tipocruzamento) throws InterruptedException {
        int instrucao = MalhaViaria.getInstance().getEstradas()[lAtual][cAtual].getInstrucao();

        switch (tipocruzamento) {
            case 1:
                cruzamento1(instrucao, sortdirecao);
                break;
            case 2:
                cruzamento2(instrucao, sortdirecao);
                break;
            case 3:
                cruzamento3(instrucao, sortdirecao);
                break;
            case 4:
                cruzamento4(instrucao, sortdirecao);
                break;
        }
    }

    private void cruzamento1(int instrucao, int sortdirecao) throws InterruptedException {
        switch (instrucao) {
            case 1:
                switch (sortdirecao) {
                    case 1:
                        moverCima();
                        moverDireita();
                        break;
                    case 2:
                        moverCima();
                        moverCima();
                        moverCima();
                        break;
                    case 3:
                        moverCima();
                        moverCima();
                        moverEsquerda();
                        moverEsquerda();
                        break;
                }
                break;
            case 2:
                switch (sortdirecao) {
                    case 1:
                        moverDireita();
                        moverBaixo();
                        break;
                    case 2:
                        moverDireita();
                        moverDireita();
                        moverDireita();
                        break;
                    case 3:
                        moverDireita();
                        moverDireita();
                        moverCima();
                        moverCima();
                        break;
                }
                break;
            case 3:
                switch (sortdirecao) {
                    case 1:
                        moverBaixo();
                        moverEsquerda();
                        break;
                    case 2:
                        moverBaixo();
                        moverBaixo();
                        moverBaixo();
                        break;
                    case 3:
                        moverBaixo();
                        moverBaixo();
                        moverDireita();
                        moverDireita();
                        break;
                }
                break;
            case 4:
                switch (sortdirecao) {
                    case 1:
                        moverEsquerda();
                        moverCima();
                        break;
                    case 2:
                        moverEsquerda();
                        moverEsquerda();
                        moverEsquerda();
                        break;
                    case 3:
                        moverEsquerda();
                        moverEsquerda();
                        moverBaixo();
                        moverBaixo();
                        break;
                }
                break;
        }
    }

    private void cruzamento2(int instrucao, int sortdirecao) throws InterruptedException {
        switch (instrucao) {
            case 1:
                switch (sortdirecao) {
                    case 1:
                        moverCima();
                        moverDireita();
                        break;
                    case 2:
                        moverCima();
                        moverCima();
                        moverEsquerda();
                        moverEsquerda();
                        break;
                }
                break;
            case 2:
                switch (sortdirecao) {
                    case 1:
                        moverDireita();
                        moverBaixo();
                        break;
                    case 2:
                        moverDireita();
                        moverDireita();
                        moverDireita();
                        break;
                }
                break;
            case 4:
                switch (sortdirecao) {
                    case 1:
                        moverEsquerda();
                        moverEsquerda();
                        moverEsquerda();
                        break;
                    case 2:
                        moverEsquerda();
                        moverEsquerda();
                        moverBaixo();
                        moverBaixo();
                        break;
                }
        }
    }

    private void cruzamento3(int instrucao, int sortdirecao) throws InterruptedException {
        switch (instrucao) {
            case 1:
                switch (sortdirecao) {
                    case 1:
                        moverCima();
                        moverCima();
                        moverCima();
                        break;
                    case 2:
                        moverCima();
                        moverCima();
                        moverEsquerda();
                        moverEsquerda();
                        break;
                }
                break;
            case 2:
                switch (sortdirecao) {
                    case 1:
                        moverDireita();
                        moverBaixo();
                        break;
                    case 2:
                        moverDireita();
                        moverDireita();
                        moverCima();
                        moverCima();
                        break;
                }
                break;
            case 3:
                switch (sortdirecao) {
                    case 1:
                        moverBaixo();
                        moverEsquerda();
                        break;
                    case 2:
                        moverBaixo();
                        moverBaixo();
                        moverBaixo();
                        break;
                }
                break;
        }
    }

    private void cruzamento4(int instrucao, int sortdirecao) throws InterruptedException {
        switch (instrucao) {
            case 1:
                switch (sortdirecao) {
                    case 1:
                        moverCima();
                        moverDireita();
                        break;
                    case 2:
                        moverCima();
                        moverCima();
                        moverCima();
                        break;
                }
                break;
            case 3:
                switch (sortdirecao) {
                    case 1:
                        moverBaixo();
                        moverBaixo();
                        moverBaixo();
                        break;
                    case 2:
                        moverBaixo();
                        moverBaixo();
                        moverDireita();
                        moverDireita();
                        break;
                }
                break;
            case 4:
                switch (sortdirecao) {
                    case 1:
                        moverEsquerda();
                        moverCima();
                        break;
                    case 2:
                        moverEsquerda();
                        moverEsquerda();
                        moverBaixo();
                        moverBaixo();
                        break;
                }
                break;
        }
    }

}
