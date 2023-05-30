package com.intelligent.bot.api.mj.support.handle;


import cn.hutool.core.text.CharSequenceUtil;
import com.intelligent.bot.api.mj.support.Task;
import com.intelligent.bot.enums.mj.TaskStatus;
import net.dv8tion.jda.api.entities.Message;

public interface MessageHandler {

	void onMessageReceived(Message message);

	void onMessageUpdate(Message message);

	default void finishTask(Task task, Message message) {
		task.setFinishTime(System.currentTimeMillis());
		if (!message.getAttachments().isEmpty()) {
			task.setStatus(TaskStatus.SUCCESS);
			String imageUrl = message.getAttachments().get(0).getUrl();
			task.setImageUrl(imageUrl);
			int hashStartIndex = imageUrl.lastIndexOf("_");
			task.setMessageHash(CharSequenceUtil.subBefore(imageUrl.substring(hashStartIndex + 1), ".", true));
		} else {
			task.setStatus(TaskStatus.FAILURE);
		}
	}

}