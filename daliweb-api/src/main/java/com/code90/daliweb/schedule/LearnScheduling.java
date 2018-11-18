package com.code90.daliweb.schedule;

import com.code90.daliweb.domain.CompetitionDetail;
import com.code90.daliweb.domain.User;
import com.code90.daliweb.domain.UserChangeLog;
import com.code90.daliweb.server.CompetitionDetailServer;
import com.code90.daliweb.server.UserServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * 学习定时器
 * @author Ray Lin
 * @create 2018-11-04 17:35
 **/
@Configuration
@EnableScheduling
public class LearnScheduling {
    private final Logger logger = LoggerFactory.getLogger(LearnScheduling.class);
    @Autowired
    private UserServer userServer;
    @Autowired
    private CompetitionDetailServer competitionDetailServer;

    @Scheduled(cron="0 30 * * * ? ")
    public void addLevel(){
        List<User> users=userServer.getAll();
        for(User user : users){
            if(user.getTeamLevel()==0&&user.getPoint()>=100){
                user.setTeamLevel(1);
                userServer.save(user);
                UserChangeLog userChangeLog=new UserChangeLog();
                userChangeLog.setType(1);
                userChangeLog.createBy=user.getUserCode();
                userServer.saveUserChangeLog(userChangeLog);
            }else if(user.getTeamLevel()==1&&user.getPoint()>=200){
                List<CompetitionDetail> competitionDetails=competitionDetailServer.getCompetitionDetailByUserCode(user.getUserCode());
                if(competitionDetails.size()>0){
                    user.setTeamLevel(2);
                    userServer.save(user);
                    UserChangeLog userChangeLog=new UserChangeLog();
                    userChangeLog.setType(2);
                    userChangeLog.createBy=user.getUserCode();
                    userServer.saveUserChangeLog(userChangeLog);
                }
            }else if(user.getTeamLevel()==2&&user.getPoint()>=300){
                List<CompetitionDetail> competitionDetails=competitionDetailServer.getCompetitionDetailByUserCode(user.getUserCode());
                for (CompetitionDetail competitionDetail:competitionDetails){
                    if(competitionDetail.getStatus()==1){
                        user.setTeamLevel(3);
                        userServer.save(user);
                        UserChangeLog userChangeLog=new UserChangeLog();
                        userChangeLog.setType(3);
                        userChangeLog.createBy=user.getUserCode();
                        userServer.saveUserChangeLog(userChangeLog);
                        break;
                    }
                }
            }
        }
    }
}
