import '../styles/globals.css'
import type { AppProps } from 'next/app'
import NavArea from "../comps/NavArea";

function MyApp({ Component, pageProps }: AppProps) {
  return (
      <NavArea>
        <Component {...pageProps} />
      </NavArea>
  )
}

export default MyApp
