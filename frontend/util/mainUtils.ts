import {ShortPosition, ShortPositionHistoryDto} from "../pages/instrumenter";

export function slugify(string: string) {
  return string.split(" ").join("-");
}

export function formatDate(string: string) {
  if(string) {
    return string.split("T")[0].split("-").reverse().join(".")
  } else {
    return string
  }
}

interface SP {
  date: string;
  // [key: string]: number
}

export function parseShortPositions(shortPositions: ShortPosition[]) {
  let res: {date: string, [key: string]}[] = []
  console.log(shortPositions)

  for(const shortPosition of shortPositions) {
    for(const history of shortPosition.history) {
      const index = res.findIndex(x => x.date === history.date)
      if(index !== -1) {
        res[index].shortPosition.companyName = history.shares;
      } else {
        const newObj = {
          date: history.date,
          [shortPosition.companyName]: history.shares
        }
        res.push(newObj)
      }
    }
  }
  return res
}