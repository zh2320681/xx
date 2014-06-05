package com.axb.android.dto;

import cn.tt100.base.ZWBo;
import cn.tt100.base.annotation.DatabaseField;
/**
 * 排行榜 类
 * @author shrek
 *
 */
public class Ranking extends ZWBo {
	//任务完成率 80.0 %
	
	@DatabaseField
	public double taskRanking;
	//每日一题 完成数目
	@DatabaseField
	public int dailyNum;
	//自学完成数目
	@DatabaseField
	public int selfNum;
}
