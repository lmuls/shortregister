import React from "react";
import { DetailDashboard } from "../../comps/DetailDashboard";
import { slugify } from "../../util/mainUtils";
import { InstrumentDTO } from "../instrumenter";

export function Instrument({
  instrumentInfo,
}: {
  instrumentInfo: InstrumentDTO;
}) {
  return (
    <DetailDashboard
      id={instrumentInfo.isin}
      name={instrumentInfo.issuerName}
      shortPositions={instrumentInfo.shortPositions}
    />
  );
}

export async function getStaticPaths() {
  const url = process.env.NEXT_PUBLIC_API_URL + "instruments";
  const res = await fetch(url);

  const json = await res.json();

  const paths = json.map((instrument: InstrumentDTO) => {
    return { params: { Instrument: slugify(instrument.issuerName) } };
  });

  return { paths: paths, fallback: false };
}

export async function getStaticProps({
  params,
}: {
  params: { Instrument: string };
}) {
  const url =
    process.env.NEXT_PUBLIC_API_URL + "instruments/" + params.Instrument;
  const res = await fetch(url);
  const instrumentInfo = await res.json();

  return { props: { instrumentInfo } };
}

export default Instrument;
