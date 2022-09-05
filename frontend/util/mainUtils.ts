import {
  ShortPositionDTO,
  ShortPositionHistoryDTO,
} from "../pages/instrumenter";

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

export const incrementColor = function (color: string) {
  let colorInt = parseInt(color); // Convert HEX color to integer
  colorInt += 50;
  const nColor = colorInt.toString();

  nColor.slice(0, 1);

  if (/^#[0-9a-f]{6}$/i.test(nColor)) {
    // Make sure that HEX is a valid color
    return nColor;
  }
  return color;
};
