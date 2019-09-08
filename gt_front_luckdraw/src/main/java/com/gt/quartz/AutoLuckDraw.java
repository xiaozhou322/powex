/*package com.gt.quartz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.NperStatusEnum;
import com.gt.entity.Lottery;
import com.gt.entity.Nper;
import com.gt.service.LotteryService;
import com.gt.service.NperService;
import com.gt.util.PowerBallUtil;
import com.gt.util.Utils;

public class AutoLuckDraw {

	@Autowired
	private LotteryService lotteryService;
	
	@Autowired
	private NperService nperService;
	
	public void work(){
		synchronized (this) {
			
			String filter = "where status="+NperStatusEnum.START_ING;
			List<Nper> list = nperService.list(0, 0, filter, false);
			if(null != list && list.size()==1){
				Nper nper = list.get(0);
				Long count = lotteryService.currentLotteryNumber(nper.getNper(),Utils.getLotteryTable(nper.getNper()));
				
				if(count >= nper.getLottery_max()){
					nper.setStatus(NperStatusEnum.DRAW_LOTTERY);
					nper.setDraw_time(Utils.getTimestamp());
					nperService.updateObj(nper);
					
					
					//int[] ball = new int[]{11,04,39,48,50,51};
					String ballStr = PowerBallUtil.getBall();
					int[] ball = PowerBallUtil.toArray(ballStr);

					int sum = 0;
					int index=ball[0]-1;
					for(int j=1;j<ball.length;j++){
						
						String sql = "SELECT CONCAT(SUBSTR(createtime,12,2),SUBSTR(createtime,15,2),SUBSTR(createtime,18,2),SUBSTR(createtime,21,3)) as time "
								+ "from Lottery  where nper ='"+nper.getNper()+"' order by createtime desc";
						List<String> lotterys = lotteryService.queryList(index, ball[j], sql, true,Utils.getLotteryTable(nper.getNper()));
						
						for(int i=0;i<ball[j];i++){
							sum += Integer.valueOf(lotterys.get(i));
						}
						
						index += 10000;
					}
					
					int result = sum%count.intValue();
					result += nper.getLottery_min();
					
					String win_no = Utils.padLeft(result+"", 9);
					List<Lottery> win = lotteryService.findByTwoProperty("nper", nper.getNper(), "lottery_no", win_no,Utils.getLotteryTable(nper.getNper()));
					if(null != win && win.size()==1){
						nper.setWin_uid(win.get(0).getUid());
					}
					nper.setWin_no(win_no);
					nper.setBall(ballStr);
					nper.setStatus(NperStatusEnum.END);
					nper.setEnd_time(Utils.getTimestamp());
					nperService.updateObj(nper);
					
					createNewNper(nper.getNper());
				}
				
			}else{
				String filter1 = "where status="+NperStatusEnum.NO_START;
				List<Nper> list2 = nperService.list(0, 0, filter1, false);
				if(null != list2 && list2.size()==1){
					Nper n = list2.get(0);
					n.setStart_time(Utils.getTimestamp());
					n.setStatus(NperStatusEnum.START_ING);
					nperService.updateObj(n);
				}
			}
			
		}
	}
	
	private void createNewNper(String nper_no){
		int new_nper_no = Integer.valueOf(nper_no)+1;
		Nper nper = new Nper();
		nper.setLottery_min(100000001);
		nper.setLottery_max(999999999);
		nper.setNper(Utils.padLeft(new_nper_no+"", 4));
		nper.setStatus(NperStatusEnum.NO_START);
		nper.setCreate_time(Utils.getTimestamp());
		nperService.saveObj(nper);
	}
	
}
*/