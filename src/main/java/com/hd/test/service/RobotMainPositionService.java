package com.hd.test.service;

import com.hd.test.common.ZStringUtils;
import com.hd.test.entity.RobotMainPosition;
import org.springframework.stereotype.Service;

@Service
public class RobotMainPositionService extends BaseService<RobotMainPosition> {

    public RobotMainPosition getByOfficeIdAndPositionId(RobotMainPosition entity) {
        return getData().stream().filter(a -> ZStringUtils.isEquals(a.getOffice().getId(), entity.getOffice().getId()) && ZStringUtils.isEquals(a.getPositionId(), entity.getPositionId()) && ZStringUtils.isEquals(a.getRobotModel(), entity.getRobotModel())).findAny().orElse(null);
    }
}
