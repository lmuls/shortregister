import { Chip } from "@mui/material";
import React from "react";
import { ShortPositionDTO } from "../pages/instrumenter";
import style from "./DetailDashboard.module.scss";
import StackedAreaChart from "./StackedAreaChart";
import { SubTable } from "./SubTable";

interface DetailProps {
  id: string;
  name: string;
  shortPositions: ShortPositionDTO[];
}

export function DetailDashboard({ id, name, shortPositions }: DetailProps) {
  const active = shortPositions.filter((x) => x.active).length > 0;

  return (
    <section id={style.detailSection}>
      <div>
        <div className={style.header}>
          <h1>{name}</h1>
          {active && (
            <Chip
              label={"Active positions"}
              variant="outlined"
              color="success"
            />
          )}
          <div className={`${style.kpi} dashboard-item`}>
            <h2>Status</h2>
            <h3>
              {active
                ? shortPositions
                    .filter((x) => x.active)
                    .reduce((previousValue, currentValue) => {
                      return previousValue + currentValue.history[0].shares;
                    }, 0)
                : 0}
              {" shorted stocks"}
            </h3>
            <h3>
              {active
                ? shortPositions
                    .filter((x) => x.active)
                    .reduce((previousValue, currentValue) => {
                      return (
                        previousValue + currentValue.history[0].shortPercent
                      );
                    }, 0)
                : 0}
              {" % shorted stocks"}
            </h3>
          </div>
        </div>
      </div>
      <div className={`${style.chart} dashboard-item`}>
        <StackedAreaChart subject={name} />
      </div>
      <div className={`${style.table} dashboard-item`}>
        <SubTable shortPositions={shortPositions} isInstrument={true} />
      </div>
    </section>
  );
}
