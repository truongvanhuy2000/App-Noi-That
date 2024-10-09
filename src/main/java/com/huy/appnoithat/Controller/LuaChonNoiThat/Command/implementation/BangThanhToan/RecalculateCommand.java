package com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation.BangThanhToan;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Command;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Memento;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableCalculationUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecalculateCommand implements Command {
    private final BangThanhToan bangThanhToan;
    private final BangThanhToan.Percentage percentage;

    private Memento bangThanhToanSnapshot;

    @Override
    public void execute() {
        bangThanhToanSnapshot = bangThanhToan.createSnapshot();
        TableCalculationUtils.calculateBangThanhToan(bangThanhToan, percentage);
    }

    @Override
    public void undo() {
        if (bangThanhToanSnapshot != null) {
            bangThanhToanSnapshot.restore();
        }
    }
}
