import { Chip } from "@mui/material";
import React from "react";
import StackedAreaChart from "../../comps/StackedAreaChart";
import { SubTable } from "../../comps/SubTable";
import { slugify } from "../../util/mainUtils";
import { Instrument } from "../instrumenter";
import style from "./Instrument.module.scss";

export function instrument({ instrumentInfo }: { instrumentInfo: Instrument }) {
  const active =
    instrumentInfo.shortPositions.filter((x) => x.active).length > 0;

  return (
    <section id={style.detailSection}>
      <div>
        <div className={style.header}>
          <h1>{instrumentInfo.issuerName}</h1>
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
                ? instrumentInfo.shortPositions
                    .filter((x) => x.active)
                    .reduce((previousValue, currentValue) => {
                      return previousValue + currentValue.history[0].shares;
                    }, 0)
                : 0}
              {" shorted stocks"}
            </h3>
            <h3>
              {active
                ? instrumentInfo.shortPositions
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
        <StackedAreaChart subject={instrumentInfo.issuerName} />
      </div>
      <div className={`${style.table} dashboard-item`}>
        <SubTable
          shortPositions={instrumentInfo.shortPositions}
          isInstrument={true}
        />
      </div>
    </section>
  );
}

export async function getStaticPaths() {
  const url = process.env.NEXT_PUBLIC_API_URL + "instruments";
  const res = await fetch(url);

  const json = await res.json();

  const paths = json.map((instrument: Instrument) => {
    return { params: { instrument: slugify(instrument.issuerName) } };
  });

  return { paths: paths, fallback: false };
}

export async function getStaticProps({
  params,
}: {
  params: { instrument: string };
}) {
  const url =
    process.env.NEXT_PUBLIC_API_URL + "instruments/" + params.instrument;
  const res = await fetch(url);
  const instrumentInfo = await res.json();

  return { props: { instrumentInfo } };
}

export default instrument;
