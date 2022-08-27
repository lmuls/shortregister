import React from "react";
import { GetStaticPaths, GetStaticProps } from "next";
import { Instrument } from "../instrumenter";
import { slugify } from "../../util/mainUtils";
import { Shorter } from "../shortere";

export function instrument({
  shorterInfo,
}: {
  shorterInfo: {
    id: number;
    companyName: string;
    shortPositionDtos: object[];
  };
}) {
  return <h1>{shorterInfo.companyName}</h1>;
}

export async function getStaticPaths() {
  const url = process.env.NEXT_PUBLIC_API_URL + "shorters";
  const res = await fetch(url);

  const json = await res.json();

  const paths = json.map((shorter: Shorter) => {
    return { params: { shorter: slugify(shorter.companyName) } };
  });

  return { paths: paths, fallback: false };
}

export async function getStaticProps({
  params,
}: {
  params: { shorter: string };
}) {
  const url = process.env.NEXT_PUBLIC_API_URL + "shorters/" + params.shorter;
  const res = await fetch(url);
  const shorterInfo = await res.json();

  return { props: { shorterInfo } };
}

export default instrument;
