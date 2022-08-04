export function slugify(string: string) {
  return string.split(" ").join("-");
}
