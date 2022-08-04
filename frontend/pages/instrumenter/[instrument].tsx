import React from "react";
import { GetStaticPaths, GetStaticProps } from "next";
import { Instrument } from "../instrumenter";
import { slugify } from "../../util/mainUtils";

export function instrument({
  instrumentInfo,
}: {
  instrumentInfo: {
    isin: string;
    issuerName: string;
    shortPositionDtos: object[];
  };
}) {
  return <h1>{instrumentInfo.issuerName}</h1>;
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
