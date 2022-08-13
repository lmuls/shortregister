import React from "react";
import { GetStaticPaths, GetStaticProps } from "next";
import StackedAreaChart from "../../comps/StackedAreaChart";
import { Instrument } from "../instrumenter";
import {parseShortPositions, slugify} from "../../util/mainUtils";
import style from "./Instrument.module.scss"

export function instrument({
  instrumentInfo,
}: {
  instrumentInfo: Instrument;
}) {

  return (
      <section id={style.detailSection}>
        <div className={style.header}>
          <h1>{instrumentInfo.issuerName}</h1>
        </div>
        <div className={style.kpi}></div>
        <div className={style.chart}><StackedAreaChart data={instrumentInfo.shortPositions} /></div>
        <div className={style.table}></div>
      </section>
  )
}

export async function getStaticPaths() {
  const url = process.env.API_URL + "api/instruments";
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
  const url = process.env.API_URL + "api/instruments/" + params.instrument;
  const res = await fetch(url);
  const instrumentInfo = await res.json();

  return { props: { instrumentInfo } };
}

export default instrument;
