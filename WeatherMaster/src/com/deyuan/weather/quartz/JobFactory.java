/**
 * 
 */
package com.deyuan.weather.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 * @ClassName JobFactory
 * @Description TODO
 * @author lerry.zhang
 * @date 2017-11-28 ����10:31:39
 */
public class JobFactory extends AdaptableJobFactory {

	// �������Spring��������Զ�ע�����,Ҳ����Spring��������.
	@Autowired
	private AutowireCapableBeanFactory capableBeanFactory;

	protected Object createJobInstance(TriggerFiredBundle bundle)
			throws Exception {
		// ���ø���ķ���
		Object jobInstance = super.createJobInstance(bundle);
		// ����ע��,������Spring�ļ���,������Ŀ��Բ鿴Spring��API.
		capableBeanFactory.autowireBean(jobInstance);
		return jobInstance;
	}

}