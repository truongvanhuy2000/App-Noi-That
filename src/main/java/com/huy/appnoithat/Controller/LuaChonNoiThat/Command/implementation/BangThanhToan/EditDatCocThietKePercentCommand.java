package com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation.BangThanhToan;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Command;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Memento;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EditDatCocThietKePercentCommand implements Command {
    private final BangThanhToan.Percentage percentage;
    private Memento percentageSnapshot;

    @Override
    public void execute() {
        String newPercentage = PopupUtils.openDialog("Thay đổi phần trăm", "", "Phần trăm");
        if (newPercentage != null) {
            percentageSnapshot = percentage.createSnapshot();
            percentage.getDatCocThietKePercentage().set(Integer.parseInt(newPercentage));
            percentage.getDatCocThiCongPercentage().set(100
                    - percentage.getDatCocThietKePercentage().getValue()
                    - percentage.getHangDenChanCongTrinhPercentage().getValue()
            );
        }
    }

    @Override
    public void undo() {
        if (percentageSnapshot != null) {
            percentageSnapshot.restore();
        }
    }
}
