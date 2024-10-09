package com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation.BangThanhToan;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Command;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Memento;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EditNghiemThuQuyetCommand implements Command {
    private final BangThanhToan.Percentage percentage;
    private Memento percentageSnapshot;

    @Override
    public void execute() {
        String newPercentage = PopupUtils.openDialog("Thay đổi phần trăm", "", "Phần trăm");
        if (newPercentage != null) {
            percentageSnapshot = percentage.createSnapshot();
            percentage.getNghiemThuQuyetPercentage().set(Integer.parseInt(newPercentage));
            percentage.getDatCocThietKePercentage().set(100
                    - percentage.getDatCocThiCongPercentage().getValue()
                    - percentage.getHangDenChanCongTrinhPercentage().getValue()
                    - percentage.getNghiemThuQuyetPercentage().getValue()
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
