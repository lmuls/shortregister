
import type { NextPage, GetServerSideProps } from 'next'
import ResponsiveAppBar from "../comps/Navbar";
import Head from 'next/head'
import Image from 'next/image'
import styles from '../styles/Home.module.scss'
import heroImg from '../public/115.jpg'
import Instruments from "./instruments";
import BasicList from "../comps/BasicList/BasicList";



export interface Instrument {
    isin: string,
    issuerName: string
}



const Home = (props: {instruments: Instrument[]}) => {
  return (
      <>
          <ResponsiveAppBar />
          <div id={styles.mainCont} >
              <Image src={heroImg} alt={"Cartonish image of a bull and a bear"} priority/>
              <div className={styles.heroCont}>
                  <h1>Welcome to the Short Register for OSEBX</h1>
                  <p>Here you will find information of all stocks shorted above 2 %.</p>
                  <div className="hero-content"></div>
              </div>
              <BasicList elements={props.instruments} />
          </div>

          <Instruments instruments={props} />
      </>
  )
}

export default Home

export const getServerSideProps: GetServerSideProps = async (context) => {
    console.log(process.env.API_URL)
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