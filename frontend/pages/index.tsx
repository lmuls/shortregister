
import type { NextPage, GetServerSideProps } from 'next'
import NavArea from "../comps/NavArea";
import Head from 'next/head'
import Image from 'next/image'
import styles from '../styles/Home.module.scss'
import heroImg from '../public/115.jpg'
import Instrumenter, {Instrument} from "./instrumenter";
import BasicList from "../comps/BasicList/BasicList";






const Home = (props: {instruments: Instrument[]}) => {
  return (
      <>
          {/*<Image src={heroImg} alt={"Cartonish image of a bull and a bear"} priority/>*/}
          <div id={styles.mainCont} >

              <div className={styles.heroCont}>
                  <h1>Welcome to the Short Register for OSEBX</h1>
                  <p>Here you will find information of all stocks shorted above 2 %.</p>
                  <div className="hero-content"></div>
              </div>
              {/*<BasicList elements={props.instruments} />*/}
          </div>
      </>
  )
}

export default Home

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