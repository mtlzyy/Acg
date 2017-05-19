package com.acg.thread;

import com.acg.dao.ActivationDao;

/**
 * 删除超过两天的待激活的注册信息
 * @author Administrator
 *
 */
public class RemoveTempUserThread extends Thread{
	private ActivationDao adao = new ActivationDao();
	public void run() {
		while(true){
			adao.removeTempUsersTwoDaysAgo();
			try {
				sleep(24*60*3600);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
