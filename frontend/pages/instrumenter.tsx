import React from "react";
import Link from "next/link";
import {GetServerSideProps} from "next";


export interface Instrument {
    isin: string,
    issuerName: string
}

function Instrumenter(props: {instruments: Instrument[]}) {
    return (
        <div className="instruments">
            <ul>
                {props && props.instruments.length > 0 && props.instruments.map(instrument => {
                    return (
                        <li key={instrument.isin}>
                            <Link href={`/instrumenter/${encodeURIComponent(instrument.isin)}`}>
                                <a>{instrument.issuerName}</a>
                            </Link>
                        </li>)
                })}
            </ul>
        </div>
    )
}

export default Instrumenter;

export const getServerSideProps: GetServerSideProps = async (context) => {
    const url = process.env.API_URL + 'api/instruments'
    const res = await fetch(url)

    let instruments : Instrument[] = []
    if(res.status == 200) {
        instruments = await res.json()
    } else {
        console.log("Error in request")
    }

    return { props: { instruments } }
}
