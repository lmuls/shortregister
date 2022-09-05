import React, { PureComponent } from "react";
import {
  AreaChart,
  Area,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
} from "recharts";

interface ChartDataDto {
  [key: string]: string;
}

interface ChartProps {
  subject: string;
}

interface ChartState {
  subject: string;
  chartData: ChartDataDto[];
  entries: string[];
}

export default class StackedAreaChart extends PureComponent<
  ChartProps,
  ChartState
> {
  state: ChartState = {
    subject: this.props.subject,
    chartData: [],
    entries: [],
  };

  async componentDidMount() {
    const res = await fetch(process.env.NEXT_PUBLIC_API_URL + "chart-data", {
      method: "POST",
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify({
        subject: this.props.subject,
      }),
    });
    const chartData: ChartDataDto[] = await res.json();
    this.setState((state) => ({ chartData: chartData }));

    const entries: string[] = [];
    chartData.forEach((inst) => {
      for (const entry of Object.getOwnPropertyNames(inst)) {
        if (entry !== "date" && !entries.includes(entry)) {
          entries.push(entry);
        }
      }
    });
    this.setState((state) => ({ entries: entries }));
  }

  render() {
    let currentColor = "#2e86abff";

    return (
      <ResponsiveContainer width="100%" height="100%">
        <AreaChart
          width={500}
          height={400}
          data={this.state.chartData}
          margin={{
            top: 10,
            right: 30,
            left: 0,
            bottom: 0,
          }}
        >
          {/*<CartesianGrid strokeDasharray="3 3" />*/}
          <XAxis dataKey={"date"} />
          <YAxis />
          <Tooltip key={"x"} />
          {this.state.entries.map((entry, index) => {
            return (
              <Area
                key={entry}
                type="monotone"
                dataKey={entry}
                stackId="1"
                stroke={currentColor}
                fill={currentColor}
              />
            );
          })}
        </AreaChart>
      </ResponsiveContainer>
    );
  }
}
