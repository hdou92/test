package com.hd.test.service;

import com.hd.test.common.StringUtils;
import com.hd.test.entity.RobotMainPosition;
import org.springframework.stereotype.Service;

@Service
public class RobotMainPositionService extends BaseService<RobotMainPosition> {

    public RobotMainPosition getByOfficeIdAndPositionId(RobotMainPosition entity) {
        return getData().stream().filter(a -> StringUtils.isEquals(a.getOffice().getId(), entity.getOffice().getId()) && StringUtils.isEquals(a.getPositionId(), entity.getPositionId()) && StringUtils.isEquals(a.getRobotModel(), entity.getRobotModel())).findAny().orElse(null);
    }
}
