import { ShortPosition, ShortPositionHistoryDto } from "../pages/instrumenter";

interface ChartData {
  date: string;
  [index: string]: string | number;
}

export function slugify(string: string) {
  return string.split(" ").join("-");
}

export function formatDate(string: string) {
  if (string) {
    return string.split("T")[0].split("-").reverse().join(".");
  } else {
    return string;
  }
}

export function parse(shortPositions: ShortPosition[]) {
  let dates = [];

  for (const shortPosition of shortPositions) {
    for (const history of shortPosition.history) {
      if (!dates.find((x) => x.date === history.date)) {
        dates.push({ date: history.date });
      }
    }
  }

  for (const object of dates) {
    for (const shortPosition of shortPositions) {
      for (const history of shortPosition.history) {
        if (history.date === object.date) {
          object[shortPosition.companyName] = history.shares;
        }
      }
    }
  }

  console.log(dates);

  return dates.sort((a, b) => {
    if (a.date > b.date) {
      return 1;
    } else {
      return -1;
    }
  });
}

export function parseShortPositions(shortPositions: ShortPosition[]) {
  let res: ChartData[] = [];
  // console.log(shortPositions);

  for (const shortPosition of shortPositions) {
    if (!shortPosition.active) {
      res.push({
        date: shortPosition.closed,
        [shortPosition.companyName]: 0,
      });
    }

    // if(res) {
    //   for(const result of res) {
    //     if (shortPosition.opened > result.date && shortPosition.closed < result.date) {
    //       result[shortPosition.companyName] =
    //     }
    //   }
    // }

    for (const history of shortPosition.history) {
      const index = res.findIndex((x) => x.date === history.date);
      if (index !== -1) {
        res[index][shortPosition.companyName] = history.shares;
      } else {
        const newObj = {
          date: formatDate(history.date),
          [shortPosition.companyName]: history.shares,
        };
        res.push(newObj);
      }
    }
  }
  return res;
}

// function fillBlankDates(res: ChartData[], shortPositions) {
//
//   for(const result of res) {
//     if(result.date > )
//   }
// }
