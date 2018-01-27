package org.goaler.ballwar.common.discover.find;

import java.util.Set;

public abstract class Find {
	protected RespondListener respListener;
	
	/**
	 * milliseconds 后返回结果
	 * @param milliseconds
	 * @return
	 */
	abstract public Set<String> find(int milliseconds);
	/**
	 * 找到服务器后调用oper,10秒没有任何服务器反馈结束监听
	 * @return
	 */
	abstract public void setRespondListener(RespondListener respListener);
	/**
	 * 关闭服务器查找线程
	 */
	abstract public void close();
	
	public interface RespondListener{
		void oper(String serverIp);
	}
}
