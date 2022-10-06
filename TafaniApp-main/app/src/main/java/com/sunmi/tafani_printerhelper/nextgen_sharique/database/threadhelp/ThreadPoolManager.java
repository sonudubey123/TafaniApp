package com.sunmi.tafani_printerhelper.nextgen_sharique.database.threadhelp;


import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程管理类
 * 
 * @author lenovo
 *
 */
public class ThreadPoolManager {
	private ExecutorService service;

	private ThreadPoolManager() {
		int num = Runtime.getRuntime().availableProcessors() * 20;
		service = Executors.newFixedThreadPool(num);
	}

	private static final com.sunmi.tafani_printerhelper.threadhelp.ThreadPoolManager manager = new com.sunmi.tafani_printerhelper.threadhelp.ThreadPoolManager();

	public static com.sunmi.tafani_printerhelper.threadhelp.ThreadPoolManager getInstance() {
		return manager;
	}

	public void executeTask(Runnable runnable) {
		service.execute(runnable);

	}

	public void executeTasks(LinkedList<Runnable> list){
		for(Runnable runnable:list){
			service.execute(runnable);
		}
	}
}
