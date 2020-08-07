package ru.job4j.partfirstmultithreading.finaltask;

@SuppressWarnings("ALL")
public class MasterSlaveBarrier {

    Boolean bMaster = true;
    Boolean bSlave = true;

    public synchronized void tryMaster() {
        bMaster = true;
        notifyAll();
    }

    public synchronized void trySlave() {
        bSlave = true;
        notifyAll();
    }

    public synchronized void doneMaster() {
        while (bMaster == true) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bMaster = false;
        }
        notify();
    }

    public synchronized void doneSlave() {
        while (bSlave == true) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bSlave = false;
        }
        notify();
    }

}