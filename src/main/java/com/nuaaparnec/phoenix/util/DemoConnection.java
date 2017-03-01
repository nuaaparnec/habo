
package com.nuaaparnec.phoenix.util;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.phoenix.jdbc.PhoenixConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoConnection extends PhoenixConnection {
    private static final Logger log           = LoggerFactory.getLogger(DemoConnection.class);

    private int                 numPerCommit  = 10;
    private int                 timePerCommit = 5000;

    private AtomicInteger       curNum        = new AtomicInteger(0);
    private long                curTime       = System.currentTimeMillis();
    private volatile boolean    needCommit    = false;

    public DemoConnection(PhoenixConnection connection, int numPerCommit) throws SQLException {
        super(connection);
        setAutoCommit(false);
        if (numPerCommit > 0) {
            this.numPerCommit = numPerCommit;
        }
        Timer timer = new Timer("commit");
        timer.schedule(new TimerTask() {
            public void run() {
                if (DemoConnection.this.needCommit)
                    try {
                        if (System.currentTimeMillis()
                                - DemoConnection.this.curTime > DemoConnection.this.timePerCommit) {
                            DemoConnection.this.commit();
                            DemoConnection.log.info("do job commit");
                        }
                    } catch (Exception e) {
                        DemoConnection.log.error("commit fail", e);
                    }
            }
        }, 0L, this.timePerCommit * 2);
    }

    public synchronized void commit() throws SQLException {
        if ((System.currentTimeMillis() - this.curTime < this.timePerCommit)
                && (this.curNum.incrementAndGet() < this.numPerCommit)) {
            this.needCommit = true;
            return;
        }

        super.commit();
        this.curNum.set(0);
        this.curTime = System.currentTimeMillis();
        this.needCommit = false;
    }

    public int getNumPerCommit() {
        return this.numPerCommit;
    }

    public void setNumPerCommit(int numPerCommit) {
        this.numPerCommit = numPerCommit;
    }

    public int getTimePerCommit() {
        return this.timePerCommit;
    }

    public void setTimePerCommit(int timePerCommit) {
        this.timePerCommit = timePerCommit;
    }
}
